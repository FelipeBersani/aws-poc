package awspock.awspock.sqs.example.consumer.repository;

import awspock.awspock.sqs.example.consumer.model.entity.Heroe;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HeroeDAO {

    private final DynamoDBMapperConfig configWithOptimisticLocking;

    @Qualifier(value = "clobber")
    private final DynamoDBMapperConfig configWithoutOptimisticLocking;

    private final AmazonDynamoDB client;

    public void create(Heroe dto) {
        DynamoDBMapper mapper = new DynamoDBMapper(client, configWithoutOptimisticLocking);
        mapper.save(dto);
    }

    public Heroe get(String id) {
        DynamoDBMapper mapper = new DynamoDBMapper(client);
        return mapper.load(Heroe.class,id);
    }

    public Heroe updateName(String id, String newName) {
        Heroe heroe = get(id);
        heroe.setName(newName);
        DynamoDBMapper mapper = new DynamoDBMapper(client, configWithOptimisticLocking);
        mapper.save(heroe);
        return heroe;
    }

}
