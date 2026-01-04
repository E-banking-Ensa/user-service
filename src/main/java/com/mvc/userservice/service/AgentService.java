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
        for(Agent agent : this.agentRepository.findAll()){
            System.out.println( ", Status: " + agent.getStatus());
        }
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
        this.agentRepository.save(agent);
        return true;
    }
    @Override
    public boolean deactivate(UUID agentId){
        if(!this.agentRepository.existsById(agentId)){
            return false;
        }
        Agent agent = this.agentRepository.findById(agentId).get();
        agent.setStatus(Status.INACTIVE);
        this.agentRepository.save(agent);
        return true;
    }
    @Override
    public boolean block(UUID agentId){
        if(!this.agentRepository.existsById(agentId)){
            return false;
        }
        Agent agent = this.agentRepository.findById(agentId).orElseThrow(null);
        if(agent==null){
            return false;
        }
        agent.setStatus(Status.SUSPENDED);
        this.agentRepository.save(agent);
        return true;
    }
    @Override
    public boolean delete(UUID agentId){
        System.out.println("Attempting to delete agent with ID: " + agentId);
        if(!this.agentRepository.existsById(agentId)){
            System.out.println("Agent with ID " + agentId + " does not exist.");
            return false;
        }
        System.out.println("Agent found. Proceeding with deletion.");
        this.agentRepository.deleteById(agentId);
        System.out.println("Agent with ID " + agentId + " has been deleted.");
        return true;
    }

}
