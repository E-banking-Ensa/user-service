package com.mvc.userservice.service.interfaces;

import com.mvc.userservice.dto.ConsentTypeDto;
import com.mvc.userservice.dto.ConsentTypeRequest;
import com.mvc.userservice.entity.ConsentType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IConsentTypeService {
    List<ConsentTypeDto> getAllConsentTypes();
    ConsentType create(ConsentTypeRequest request);
    List<ConsentType> getAllActiveConsentTypes();
    Optional<ConsentType> findByCode(String code);
    Optional<ConsentType> findById(UUID id);
    void deleteConsentType(UUID id);
    boolean activateConsentType(UUID id);
    boolean deactivateConsentType(UUID id);
}
