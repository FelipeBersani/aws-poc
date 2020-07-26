package awspock.poc.sqs.example.common.model.dto;

import lombok.Data;

@Data
public class HeroeDTO {

    private String id;
    private String name;
    private Double power;
    private Boolean isAlive;

}
