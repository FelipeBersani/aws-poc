package awspock.poc.sqs.example.dynamoDB.repository;

import awspock.poc.sqs.example.common.exception.HeroeNotFoundException;
import awspock.poc.sqs.example.common.model.dto.HeroeDTO;
import awspock.poc.sqs.example.common.model.entity.Heroe;
import awspock.poc.sqs.example.common.model.mapper.HeroeMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.text.MessageFormat;
import java.util.Objects;

@Repository
@Slf4j
public class HeroeDAO {

    private final AmazonDynamoDB client;
    private final DynamoDBMapper mapper;

    public HeroeDAO(AmazonDynamoDB client) {
        this.client = client;
        this.mapper = new DynamoDBMapper(client);
    }

    public static final String HEROE_NOT_FOUND = "Heroe with id [{0}] was not found";

    public void create(Heroe heroe) {
        mapper.save(heroe);
        log.info(heroe.getName()+" was saved successfully");
    }

    public Heroe get(String heroeId) {
        Heroe heroe = mapper.load(Heroe.class, heroeId);
        if (Objects.isNull(heroe)) {
            log.error(MessageFormat.format(HEROE_NOT_FOUND, heroeId));
            throw new HeroeNotFoundException(MessageFormat.format(HEROE_NOT_FOUND, heroeId));
        }
        log.info(heroe.getName()+" is found!");
        return heroe;
    }

//    public List<Heroe> getAll(String heroName){
//        Map<String,String> expressionAttributesNames = new HashMap<>();
//        expressionAttributesNames.put("#name","name");
//
//        Map<String,AttributeValue> expressionAttributeValues = new HashMap<>();
//        expressionAttributeValues.put(":name",new AttributeValue().withS(heroName));
//
//        DynamoDBQueryExpression<Heroe> dynamoDBQueryExpression = new DynamoDBQueryExpression<Heroe>()
//          .withIndexName(Heroe.HEROE_NAME)
//          .withKeyConditionExpression("#name = :name")
//          .withExpressionAttributeNames(expressionAttributesNames)
//          .withExpressionAttributeValues(expressionAttributeValues)
//          .withConsistentRead(false);
//
//        List<Heroe> load = mapper.query(Heroe.class, dynamoDBQueryExpression);
//        return load;
//    }

    public void updateName(HeroeDTO heroeDTO) {
        Heroe heroe = HeroeMapper.of(get(heroeDTO.getId()), heroeDTO);
        mapper.save(heroe);
        log.info(heroeDTO.getName()+" updated successfully");
    }

}
