package awspock.poc.sqs.example.common.model.mapper;

import awspock.poc.sqs.example.common.model.dto.HeroeDTO;
import awspock.poc.sqs.example.common.model.entity.Heroe;
import lombok.Data;

import java.util.UUID;

@Data
public class HeroeMapper {

    public static Heroe of(HeroeDTO heroeDTO) {
        Heroe heroe = new Heroe();
        heroe.setId(UUID.randomUUID().toString());
        heroe.setName(heroeDTO.getName());
        heroe.setPower(heroeDTO.getPower());
        heroe.setIsAlive(heroeDTO.getIsAlive());
        return heroe;
    }

    public static Heroe of(Heroe heroe, HeroeDTO heroeDTO) {
        heroe.setId(heroeDTO.getId());
        heroe.setName(heroeDTO.getName());
        heroe.setPower(heroeDTO.getPower());
        heroe.setIsAlive(heroeDTO.getIsAlive());
        return heroe;
    }

}
