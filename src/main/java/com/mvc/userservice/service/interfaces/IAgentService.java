package com.mvc.userservice.service.interfaces;

import com.mvc.userservice.dto.AgentDto;

import java.util.List;
import java.util.UUID;

public interface IAgentService {
    List<AgentDto> getAllAgents();
    boolean activate(UUID agentId);
    boolean deactivate(UUID agentId);
    boolean block(UUID agentId);
}
