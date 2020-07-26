package awspock.poc.sqs.example.common.config;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableRetry
@RequiredArgsConstructor
//@EnableDynamoDBRepositories
//        (basePackages = "awspock.awspock.sqs.example.dynamoDB.repository")
public class AwsConfig {

    @Bean
    public ClientConfiguration clientConfiguration() {
        final ClientConfiguration clientConfig = new ClientConfiguration();

//        if (proxyProperties.isEnabled()) {
//            clientConfig.setProxyHost(proxyProperties.getHost());
//            clientConfig.setProxyPort(proxyProperties.getPort());
//        }

        return clientConfig;
    }

    @Bean
    public AmazonS3 amazonS3(final ClientConfiguration clientConfiguration) {
        return AmazonS3ClientBuilder.standard().withClientConfiguration(clientConfiguration).build();
    }

    @Bean
    public AmazonSQS amazonSQS(final ClientConfiguration clientConfiguration) {
        return AmazonSQSClientBuilder.standard().withClientConfiguration(clientConfiguration).build();
    }

    @Bean
    public AmazonDynamoDB amazonDynamoDB(final ClientConfiguration clientConfiguration) {
        return AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
    }

    @Bean
    public DynamoDBMapper dynamoDBMapper(final AmazonDynamoDB amazonDynamoDB) {
        return new DynamoDBMapper(amazonDynamoDB);
    }
}
