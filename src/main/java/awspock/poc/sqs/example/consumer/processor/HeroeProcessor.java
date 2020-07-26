package awspock.poc.sqs.example.consumer.processor;

import awspock.poc.sqs.example.common.model.dto.HeroeDTO;
import awspock.poc.sqs.example.common.model.mapper.HeroeMapper;
import awspock.poc.sqs.example.dynamoDB.repository.HeroeDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class HeroeProcessor {

    private final HeroeDAO heroeDAO;

    private final ObjectMapper objectMapper;

    public void processHeroe(String heroeRequest) {
        HeroeDTO heroeDTO = null;
        try {
            heroeDTO = objectMapper.readValue(heroeRequest, HeroeDTO.class);
            log.info(heroeDTO.getName() + " has " + heroeDTO.getPower() + " of power and alive is " + !heroeDTO.getIsAlive());
            Thread.sleep(1500);

            heroeDAO.create(HeroeMapper.of(heroeDTO));

        } catch (JsonProcessingException e) {
            log.error("Error on parse the request to object" + e.getStackTrace());
        } catch (InterruptedException e) {
            log.error("Error on sleep 2s" + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
