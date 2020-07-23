package awspock.awspock.sqs.example.consumer.service;

import awspock.awspock.sqs.example.consumer.model.dto.HeroeDTO;
import awspock.awspock.sqs.example.consumer.model.entity.Heroe;
import awspock.awspock.sqs.example.consumer.repository.HeroeDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class HeroeService {

    private final ObjectMapper objectMapper;
//    private final HeroeRepository heroeRepository;
    private final HeroeDAO heroeDAO;

    public void processHeroe(String heroeRequest){
        HeroeDTO heroeDTO = null;
        try {
            heroeDTO = objectMapper.readValue(heroeRequest, HeroeDTO.class);
            System.out.println(heroeDTO.getName()+" has "+heroeDTO.getPower()+" of power and alive is "+!heroeDTO.getIsAlive());
            Thread.sleep(1500);

            saveHeroe(heroeDTO);

        } catch (JsonProcessingException e) {
            log.error("Error on parse the request to object" + e.getStackTrace());
        } catch (InterruptedException e) {
            log.error("Error on sleep 2s" + e.getMessage());
        }
    }

    public void saveHeroe(HeroeDTO heroeDTO){
        heroeDAO.create(Heroe.of(heroeDTO));
//        heroeRepository.save(Heroe.of(heroeDTO));
        log.info("Heroe saved successfully");
    }

}
