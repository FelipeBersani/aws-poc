package awspock.awspock.sqs.example.consumer.controller;

import awspock.awspock.sqs.example.consumer.model.dto.HeroeDTO;
import awspock.awspock.sqs.example.consumer.service.HeroeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HeroeController {

    private HeroeService heroeService;

    @PostMapping
    public ResponseEntity<HttpStatus> saveHeroe(HeroeDTO heroeDTO){
        heroeService.saveHeroe(heroeDTO);
        return ResponseEntity.ok().build();
    }

}
