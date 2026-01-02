package com.mvc.userservice.service;

import com.mvc.userservice.dto.AgentDto;
import com.mvc.userservice.entity.Agent;
import com.mvc.userservice.enums.Status;
import com.mvc.userservice.repository.AgentRepository;
import com.mvc.userservice.service.interfaces.IAgentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AgentService implements IAgentService {
    private final AgentRepository agentRepository;
    @Override
    public List<AgentDto> getAllAgents() {
        return this.agentRepository.findAll()
                .stream()
                .map(AgentDto::fromEntity)
                .toList();
    }
    @Override
    public boolean activate(UUID agentId){
        if(!this.agentRepository.existsById(agentId)){
            return false;
        }
        Agent agent = this.agentRepository.findById(agentId).get();
        agent.setStatus(Status.ACTIVE);
        return true;
    }
    @Override
    public boolean deactivate(UUID agentId){
        if(!this.agentRepository.existsById(agentId)){
            return false;
        }
        Agent agent = this.agentRepository.findById(agentId).get();
        agent.setStatus(Status.INACTIVE);
        return true;
    }
    @Override
    public  boolean block(UUID agentId){
        if(!this.agentRepository.existsById(agentId)){
            return false;
        }
        Agent agent = this.agentRepository.findById(agentId).get();
        agent.setStatus(Status.SUSPENDED);
        return true;
    }

}
