package awspock.poc.sqs.example.common.service;

import awspock.poc.sqs.example.common.model.dto.HeroeDTO;
import awspock.poc.sqs.example.common.model.entity.Heroe;
import awspock.poc.sqs.example.common.model.mapper.HeroeMapper;
import awspock.poc.sqs.example.dynamoDB.repository.HeroeDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HeroeService {

    private final HeroeDAO heroeDAO;

    public void saveHeroe(HeroeDTO heroeDTO) {
        heroeDAO.create(HeroeMapper.of(heroeDTO));
    }

    public Heroe getHeroe(String key) {
        return heroeDAO.get(key);
    }

    public List<Heroe> getAllFromName(String heroeName){
        return heroeDAO.getAllFromName(heroeName);
    }

    public List<Heroe> getAllFromIsALive(Boolean isAlive){ return heroeDAO.getAllFromIsAlive(isAlive); }

    public void updateHeroe(HeroeDTO heroeDTO) {
        heroeDAO.updateName(heroeDTO);
    }
}
