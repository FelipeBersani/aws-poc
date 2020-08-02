package awspock.poc.sqs.example.common.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("sqs.dynamo")
@Data
@Component
public class SqsQueue {
    private String queueUrl;
    private String concurrency;
}
