package com.mvc.userservice.consumer;

import com.mvc.userservice.dto.CreateUserRequestDto;
import com.mvc.userservice.event.UserCreatedEvent;
import com.mvc.userservice.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserEventConsumer {

    private final IUserService userService;

    @KafkaListener(topics = "user-created-topic", groupId = "user-service-group")
    public void handleUserCreated(UserCreatedEvent event) {
        CreateUserRequestDto dto = event.dto();

        log.info("Événement Kafka reçu : création utilisateur {} (Keycloak ID: {})", dto.username());

        try {
            userService.createUser(dto);
            log.info("Utilisateur {} synchronisé avec succès via Kafka", dto.username());
        } catch (Exception e) {
            log.error("Erreur lors de la synchronisation de l'utilisateur {} : {}", dto.username(), e.getMessage(), e);
            // À améliorer plus tard avec retry ou DLQ
        }
    }
}