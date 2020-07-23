package awspock.awspock.sqs.example.consumer.listener;

import awspock.awspock.sqs.example.consumer.service.HeroeService;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventListener {

    private final HeroeService heroeService;

    @JmsListener(destination = "${sqs.super_heroes.name}", concurrency = "1")
    private void process(String request){
        heroeService.processHeroe(request);
    }

}

