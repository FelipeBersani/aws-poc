package awspock.awspock.sqs.example.consumer.model.entity;

import awspock.awspock.sqs.example.consumer.model.dto.HeroeDTO;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@DynamoDBTable(tableName = "Heroe")
public class Heroe {

    private static final String HEROE_NAME = "name-index";
    private static final String HEROE_ALIVE = "isAlive-index";

    @DynamoDBHashKey(attributeName = "id")
    private String id;

    @DynamoDBAttribute(attributeName = "name")
    private String name;

    @DynamoDBAttribute(attributeName = "power")
    private Double power;

    @DynamoDBAttribute(attributeName = "isAlive")
    private Boolean isAlive;

    public static Heroe of(HeroeDTO heroeDTO){
        return Heroe.builder()
                .id(UUID.randomUUID().toString())
                .name(heroeDTO.getName())
                .power(heroeDTO.getPower())
                .isAlive(heroeDTO.getIsAlive())
                .build();
    }
}
