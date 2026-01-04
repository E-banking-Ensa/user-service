package com.mvc.userservice.service.interfaces;

import com.mvc.userservice.dto.ClientDto;
import com.mvc.userservice.entity.Client;

import java.util.List;
import java.util.UUID;

public interface IClientService {
    List<ClientDto> getAllClients();
    ClientDto getClientById(UUID id);
    boolean activate(UUID id);
    boolean deactivate(UUID id);
}
