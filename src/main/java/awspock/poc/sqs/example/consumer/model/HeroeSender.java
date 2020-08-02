package awspock.poc.sqs.example.consumer.model;

import awspock.poc.sqs.example.common.model.dto.HeroeDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class HeroeSender {

    @JsonProperty("body")
    private HeroeDTO heroeDTO;

}
