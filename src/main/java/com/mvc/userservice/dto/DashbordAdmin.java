package com.mvc.userservice.dto;

import lombok.Data;

@Data
public class DashbordAdmin {
    private long agents;//total des agents
    private long consents;//total des consentements
    private long clients;//total des clients
    private long comptes;//total des comptes
}
