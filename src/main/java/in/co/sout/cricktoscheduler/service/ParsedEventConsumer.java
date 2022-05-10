package in.co.sout.cricktoscheduler.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ParsedEventConsumer {

    @KafkaListener(topics = "${parsed.event.topic:parsedTopic}", groupId = "${kafka.group.id:local}")
    public void listenGroupFoo(String message) {
        System.out.println("Received Message in group foo: " + message);
    }
}
