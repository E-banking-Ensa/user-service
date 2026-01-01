package com.mvc.userservice.config;

import com.mvc.userservice.test.KafkaTestProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaStartupTest {

    private final KafkaTestProducer producer;

    @EventListener(ApplicationReadyEvent.class)
    public void sendOnStartup() {
        producer.sendTestClientEvent();
    }
}
