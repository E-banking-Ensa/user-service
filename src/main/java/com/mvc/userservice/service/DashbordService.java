package com.mvc.userservice.service;

import com.mvc.userservice.dto.DashbordAdmin;
import com.mvc.userservice.dto.DashbordAgent;
import com.mvc.userservice.entity.Agent;
import com.mvc.userservice.repository.AgentRepository;
import com.mvc.userservice.repository.ClientRepository;
import com.mvc.userservice.repository.ConsentRepository;
import com.mvc.userservice.service.interfaces.IDashbordService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DashbordService implements IDashbordService {
    private final ClientRepository clientRepository;
    private final AgentRepository agentRepository;
    private final ConsentRepository consentRepository;

    @Override
    public DashbordAdmin getDashbordAdmin(){
        DashbordAdmin dashbordAdmin= new DashbordAdmin();
        dashbordAdmin.setClients(this.clientRepository.count());
        dashbordAdmin.setAgents(this.agentRepository.count());
        dashbordAdmin.setConsents(this.consentRepository.count());
        dashbordAdmin.setComptes(110);
        return dashbordAdmin;
    }
    @Override
    public DashbordAgent getDashbordAgent(String username){
        Agent agent=this.agentRepository.findByUsername(username);
        if(agent!=null){
            DashbordAgent dashbordAgent= new DashbordAgent();
            dashbordAgent.setClients(this.clientRepository.count());
            dashbordAgent.setConsentes(this.consentRepository.count());
            //dashbordAgent.setComptes(50);
            dashbordAgent.setAgentName(agent.getFirstName()+" "+agent.getLastName());
            dashbordAgent.setAgentEmail(agent.getEmail());
            return dashbordAgent;
        }
        return null;
    }
}
