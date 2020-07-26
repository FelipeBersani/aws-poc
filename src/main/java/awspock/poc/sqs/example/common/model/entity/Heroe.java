package awspock.poc.sqs.example.common.model.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperFieldModel;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTyped;
import lombok.Data;

@Data
@DynamoDBTable(tableName = "Heroe")
public class Heroe {

    public static final String HEROE_NAME = "name-index";

    @DynamoDBHashKey(attributeName = "id")
    private String id;

    @DynamoDBIndexHashKey(attributeName = "name", globalSecondaryIndexName = HEROE_NAME)
    private String name;

    @DynamoDBAttribute(attributeName = "power")
    private Double power;

    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.BOOL)
    @DynamoDBAttribute(attributeName = "isAlive")
    private Boolean isAlive;
}
