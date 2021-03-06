package awspock.poc.sqs.example.dynamoDB.controller;

import awspock.poc.sqs.example.common.model.dto.HeroeDTO;
import awspock.poc.sqs.example.common.model.entity.Heroe;
import awspock.poc.sqs.example.common.service.HeroeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/heroes")
public class HeroeController {

    private final HeroeService heroeService;

    @PostMapping
    public ResponseEntity<Heroe> saveHeroe(@RequestBody HeroeDTO heroeDTO) {
        heroeService.saveHeroe(heroeDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Heroe> updateHeroe(@RequestBody HeroeDTO heroeDTO) {
        heroeService.updateHeroe(heroeDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Heroe> getHeroe(@PathVariable("id") String id) {
        return ResponseEntity.ok(heroeService.getHeroe(id));
    }

    @GetMapping("/{name}/name")
    public ResponseEntity<List<Heroe>> getAllHeroesFromName(@PathVariable("name") String heroeName) {
        return ResponseEntity.ok(heroeService.getAllFromName(heroeName));
    }

    @GetMapping("/{isAlive}/isAlive")
    public ResponseEntity<List<Heroe>> getAllHeroesFromIsAlive(@PathVariable("isAlive") Boolean isAlive) {
        return ResponseEntity.ok(heroeService.getAllFromIsALive(isAlive));
    }

}
