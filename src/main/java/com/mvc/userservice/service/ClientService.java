package com.mvc.userservice.service;

import com.mvc.userservice.dto.ClientDto;
import com.mvc.userservice.entity.Client;
import com.mvc.userservice.repository.ClientRepository;
import com.mvc.userservice.service.interfaces.IClientService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ClientService implements IClientService {
    private final ClientRepository clientRepository;
    @Override
    public List<ClientDto> getAllClients(){
        return this.clientRepository.findAll().stream()
                .map(ClientDto::fromEntity)
                .toList();
    }
    @Override
    public ClientDto getClientById(UUID id){
        return ClientDto.fromEntity(this.clientRepository.findById(id).get());
    }
}
