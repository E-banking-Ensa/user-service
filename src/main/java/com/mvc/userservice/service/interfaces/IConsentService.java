package com.mvc.userservice.service.interfaces;

import com.mvc.userservice.dto.ConsentDto;
//import com.mvc.userservice.enums.ConsentType;
import com.mvc.userservice.entity.ConsentType;
import java.util.List;
import java.util.UUID;

public interface IConsentService {
    boolean addConsent(UUID userId,String consentTypeName);
    boolean deleteConsent(UUID userId, String consentTypeName);
    List<ConsentDto> getConsentsByUserId(UUID userId);
}
