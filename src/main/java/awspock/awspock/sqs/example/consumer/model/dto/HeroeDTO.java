package awspock.awspock.sqs.example.consumer.model.dto;

import lombok.Data;

@Data
public class HeroeDTO {

    private String name;
    private Double power;
    private Boolean isAlive;

}
