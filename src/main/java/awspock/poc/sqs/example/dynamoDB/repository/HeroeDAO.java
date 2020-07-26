package awspock.poc.sqs.example.dynamoDB.repository;

import awspock.poc.sqs.example.common.exception.HeroeNotFoundException;
import awspock.poc.sqs.example.common.model.dto.HeroeDTO;
import awspock.poc.sqs.example.common.model.entity.Heroe;
import awspock.poc.sqs.example.common.model.mapper.HeroeMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
@Slf4j
public class HeroeDAO {

    private static final String NULL = "null";
    private static final String DYNAMO_DB_INDEX_HASH_KEY = "DynamoDBIndexHashKey";
    private final AmazonDynamoDB client;
    private final DynamoDBMapper mapper;

    public HeroeDAO(AmazonDynamoDB client) {
        this.client = client;
        this.mapper = new DynamoDBMapper(client);
    }

    public static final String HEROE_NOT_FOUND = "Heroe with id [{0}] was not found";

    public void create(Heroe heroe) {
        mapper.save(heroe);
        log.info(heroe.getName() + " was saved successfully");
    }

    public Heroe get(String heroeId) {
        Heroe heroe = mapper.load(Heroe.class, heroeId);
        if (Objects.isNull(heroe)) {
            log.error(MessageFormat.format(HEROE_NOT_FOUND, heroeId));
            throw new HeroeNotFoundException(MessageFormat.format(HEROE_NOT_FOUND, heroeId));
        }
        log.info(heroe.getName() + " is found!");
        return heroe;
    }

    public List<Heroe> getAllFromName(String heroeName) {
        Map<String, String> expressionAttributesNames = new HashMap<>();
        expressionAttributesNames.put("#name","name");

        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":name",new AttributeValue().withS(heroeName));

        DynamoDBQueryExpression<Heroe> dynamoDBQueryExpression = buildFilter(heroeName);
//        DynamoDBQueryExpression dynamoDBQueryExpression = buildFilterWithReflection(heroName, Conditions.EQ.getCondition(), expressionAttributesNames, expressionAttributeValues);

        PaginatedQueryList<Heroe> heroes = mapper.query(Heroe.class, dynamoDBQueryExpression);
        log.info(heroes.size() + " item(s) found!");
        return heroes;
    }

    public List<Heroe> getAllFromIsAlive(Boolean isAlive){
        Map<String, String> expressionAttributesNames = new HashMap<>();
        expressionAttributesNames.put("#isAlive","isAlive");

        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":isAlive",new AttributeValue().withBOOL(isAlive));

        DynamoDBScanExpression dynamoDBQueryExpression = new DynamoDBScanExpression()
          .withFilterExpression("#isAlive = :isAlive")
          .withExpressionAttributeNames(expressionAttributesNames)
          .withExpressionAttributeValues(expressionAttributeValues)
          .withConsistentRead(false);

        PaginatedScanList<Heroe> heroes = mapper.scan(Heroe.class, dynamoDBQueryExpression);
        log.info(heroes.size() + " item(s) found!");
        return heroes;
    }

    public void updateName(HeroeDTO heroeDTO) {
        Heroe heroe = HeroeMapper.of(get(heroeDTO.getId()), heroeDTO);
        mapper.save(heroe);
        log.info(heroeDTO.getName() + " updated successfully");
    }

    private DynamoDBQueryExpression<Heroe> buildFilter(String heroeName){
        Map<String, String> expressionAttributesNames = new HashMap<>();
        expressionAttributesNames.put("#name","name");

        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":name",new AttributeValue().withS(heroeName));

        return new DynamoDBQueryExpression<Heroe>()
          .withIndexName(Heroe.HEROE_NAME)
          .withKeyConditionExpression("#name = :name")
          .withExpressionAttributeNames(expressionAttributesNames)
          .withExpressionAttributeValues(expressionAttributeValues)
          .withConsistentRead(false);
    }


    private DynamoDBQueryExpression buildFilterWithReflection(Heroe heroe, String condition, Map<String, String> attributeName, Map<String, AttributeValue> attributeValueExpression) {
        Field[] fields = heroe.getClass().getDeclaredFields();
        DynamoDBQueryExpression<Heroe> dynamoDBQueryExpression = new DynamoDBQueryExpression<Heroe>();

        for (Field field : fields) {
            try {
                field.setAccessible(true);
                String o = String.valueOf(field.get(heroe));
                if (!NULL.equalsIgnoreCase(o)) {
                    Annotation[] annotations = field.getDeclaredAnnotations();
                    if (annotations.length > 0) {
                        AttributeValue attributeValue = new AttributeValue();
                        attributeValue = fillAttributeValueType(field.getType().getSimpleName(), o, attributeValue);

                        attributeName.put("#".concat(field.getName()), field.getName());
                        attributeValueExpression.put(":".concat(field.getName()), attributeValue);

                        String declaredAnnotations = annotations[0].annotationType().getSimpleName();
                        if (declaredAnnotations.equals(DYNAMO_DB_INDEX_HASH_KEY)) {
                            dynamoDBQueryExpression.withKeyConditionExpression("#".concat(field.getName())
                              .concat(" " + condition).concat(" :".concat(field.getName())));
                        } else {
                            dynamoDBQueryExpression.withFilterExpression("#".concat(field.getName())
                              .concat(" " + condition).concat(" :".concat(field.getName())));
                        }
                    }

                }
            } catch (IllegalAccessException e) {
                log.error("Error to recover attribute value from real object");
            }
        }
        dynamoDBQueryExpression.withIndexName(Heroe.HEROE_NAME);
        dynamoDBQueryExpression.withExpressionAttributeNames(attributeName);
        dynamoDBQueryExpression.withExpressionAttributeValues(attributeValueExpression);
        dynamoDBQueryExpression.withConsistentRead(false);
        return dynamoDBQueryExpression;
    }

    public AttributeValue fillAttributeValueType(String fieldType, String value, AttributeValue attributeValue) {
        switch (fieldType) {
            case "String":
                return attributeValue.withS(value);

            case "Double":
                return attributeValue.withN(value);

            case "Integer":
                return attributeValue.withN(value);

            case "Boolean":
                return attributeValue.withBOOL(Boolean.valueOf(value));

            case "LocalDate":
                return attributeValue.withS(value);

            default:
                return attributeValue.withS(value);
        }
    }

}
