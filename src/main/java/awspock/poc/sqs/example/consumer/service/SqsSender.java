package awspock.poc.sqs.example.consumer.service;

import awspock.poc.sqs.example.common.config.properties.SqsQueue;
import awspock.poc.sqs.example.common.model.dto.HeroeDTO;
import awspock.poc.sqs.example.consumer.model.HeroeSender;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SqsSender {

    private final AmazonSQSClient sqsClient;
    private final ObjectMapper objectMapper;
    private final SqsQueue queue;

    public void sendMessage(HeroeDTO heroeDTO){
        HeroeSender heroeSender = new HeroeSender();
        heroeSender.setHeroeDTO(heroeDTO);
        try {
            String json = objectMapper.writeValueAsString(heroeSender);
            log.info("Object converted to "+json);
            sqsClient.sendMessage(queue.getQueueUrl(), json);
            log.info("Send message sucessfully");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
