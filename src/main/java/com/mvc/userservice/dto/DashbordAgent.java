package com.mvc.userservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DashbordAgent {
    private long clients;
    private long consentes;
    private String agentName;
    private String agentEmail;
    private LocalDateTime createdAt;
}
