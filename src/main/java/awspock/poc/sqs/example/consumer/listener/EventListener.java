package awspock.poc.sqs.example.consumer.listener;

import awspock.poc.sqs.example.consumer.processor.HeroeProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventListener {

    private final HeroeProcessor heroeProcessor;

    @JmsListener(destination = "${sqs.super_heroes.name}", concurrency = "1")
    private void process(String request) {
        heroeProcessor.processHeroe(request);
    }

}

