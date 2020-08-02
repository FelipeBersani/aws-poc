package awspock.poc.sqs.example.consumer.processor;

import awspock.poc.sqs.example.common.model.dto.HeroeDTO;
import awspock.poc.sqs.example.consumer.service.SqsSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class HeroeProcessor {

    private final SqsSender sqsSender;

    private final ObjectMapper objectMapper;

    public void processHeroe(String heroeRequest) {
        HeroeDTO heroeDTO = null;
        try {
            heroeDTO = objectMapper.readValue(heroeRequest, HeroeDTO.class);
            heroeDTO.setId(UUID.randomUUID().toString());
            log.info(heroeDTO.getName() + " has " + heroeDTO.getPower() + " of power and alive is " + !heroeDTO.getIsAlive());
            sqsSender.sendMessage(heroeDTO);
        } catch (JsonProcessingException e) {
            log.error("Error on parse the request to object" + e.getStackTrace());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
