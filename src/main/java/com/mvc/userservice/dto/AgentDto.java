package com.mvc.userservice.dto;

import com.mvc.userservice.entity.Agent;
import com.mvc.userservice.enums.Status;

import java.time.LocalDateTime;
import java.util.UUID;

public record AgentDto(
        UUID agentId,
        LocalDateTime createdAt,
        String firstName,
        String lastName,
        String phoneNumber,
        String email,
        String adresse,
        Status status
) {
    public static AgentDto fromEntity(Agent agent){
        System.out.println("Mapping Agent entity to AgentDto: " + agent.getLastName());
        return new AgentDto(
                agent.getId(),
                agent.getCreatedAt(),
                agent.getFirstName(),
                agent.getLastName(),
                agent.getPhoneNumber(),
                agent.getEmail(),
                agent.getAdresse(),
                agent.getStatus()
        );
    }
}
