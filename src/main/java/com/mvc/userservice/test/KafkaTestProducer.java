package com.mvc.userservice.test;

import com.mvc.userservice.dto.CreateUserRequestDto;
import com.mvc.userservice.enums.UserRole;
import com.mvc.userservice.event.UserCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaTestProducer {
    private final KafkaTemplate<String, UserCreatedEvent> kafkaTemplate;
    public void sendTestClientEvent() {
        CreateUserRequestDto dto = new CreateUserRequestDto(
//                UUID.randomUUID(),
                "clientkafka",
                "clientkafka@ebank.com",
                28,
                "Client Kafka firstname",
                "Client Kafka lastname",
                "+33698765432",
                UserRole.Client,
                "456 Avenue Test, Lyon"
        );

        UserCreatedEvent event = new UserCreatedEvent(dto);
        kafkaTemplate.send("user-created-topic", event);
        log.info("Événement test Client envoyé sur Kafka : {}", dto.username());
    }

    public void sendTestAgentEvent() {
        CreateUserRequestDto dto = new CreateUserRequestDto(
//                UUID.randomUUID(),
                "agentkafka",
                "agentkafka@ebank.com",
                35,
                "Agent Kafka Test",
                "Agent Kafka Lastname",
                "+33611112222",
                UserRole.Agent,
                "Agence Centre Ville"
        );

        UserCreatedEvent event = new UserCreatedEvent(dto);
        kafkaTemplate.send("user-created-topic", event);
        log.info("Événement test Agent envoyé sur Kafka : {}", dto.username());
    }
}