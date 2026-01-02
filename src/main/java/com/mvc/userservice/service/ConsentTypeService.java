package com.mvc.userservice.service;

import com.mvc.userservice.dto.ConsentTypeDto;
import com.mvc.userservice.dto.ConsentTypeRequest;
import com.mvc.userservice.entity.ConsentType;
import com.mvc.userservice.repository.ConsentTypeRepository;
import com.mvc.userservice.service.interfaces.IConsentTypeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ConsentTypeService implements IConsentTypeService {
    private final ConsentTypeRepository consentTypeRepository;
    @Override
    public List<ConsentTypeDto> getAllConsentTypes(){
        List<ConsentType> consentTypes = consentTypeRepository.findAll();
        return consentTypes.stream()
                .map(ConsentTypeDto::fromEntity)
                .toList();
    }
    @Override
    public ConsentType create(ConsentTypeRequest request){
        ConsentType consentType = new ConsentType();
        consentType.setCode(request.code());
        consentType.setActive(true);
        return consentTypeRepository.save(consentType);
    }
    @Override
    public List<ConsentType> getAllActiveConsentTypes(){
        return consentTypeRepository.findAll();
    }
    @Override
    public Optional<ConsentType> findById(UUID id){
        return consentTypeRepository.findById(id);
    }
    @Override
    public Optional<ConsentType> findByCode(String code){
        return consentTypeRepository.findByCode(code);
    }
    @Override
    public void deleteConsentType(UUID id){
        ConsentType consentType = consentTypeRepository.findById(id).orElse(null);
        if(consentType!=null && !consentType.isActive() && consentType.getNbr()==0){
            this.consentTypeRepository.delete(consentType);
        }
    }
    @Override
    public boolean activateConsentType(UUID id){
        ConsentType consentType = consentTypeRepository.findById(id).orElse(null);
        if(consentType==null || consentType.isActive()){
            System.out.println("here");
            return false;
        }
        consentType.setActive(true);
        this.consentTypeRepository.save(consentType);
        return true;
    }
    @Override
    public  boolean deactivateConsentType(UUID id){
        ConsentType consentType = consentTypeRepository.findById(id).orElse(null);
        if(consentType==null || !consentType.isActive()){
            System.out.println("here");
            return false;
        }
        consentType.setActive(false);
        this.consentTypeRepository.save(consentType);
        return true;
    }
}
