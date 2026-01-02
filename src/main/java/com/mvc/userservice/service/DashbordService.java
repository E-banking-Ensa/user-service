package com.mvc.userservice.service;

import com.mvc.userservice.dto.DashbordAdmin;
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
}
