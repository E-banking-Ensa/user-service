package com.mvc.userservice.service;

import com.mvc.userservice.dto.ConsentDto;
import com.mvc.userservice.entity.Consent;
import com.mvc.userservice.entity.ConsentType;
import com.mvc.userservice.entity.User;
//import com.mvc.userservice.enums.ConsentType;
import com.mvc.userservice.repository.ConsentRepository;
import com.mvc.userservice.repository.ConsentTypeRepository;
import com.mvc.userservice.repository.UserRepository;
import com.mvc.userservice.service.interfaces.IConsentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class ConsentService implements IConsentService {
    private final UserRepository userRepository;
    private final ConsentRepository consentRepository;
    private final ConsentTypeRepository consentTypeRepository;
    @Override
    @Transactional
    public boolean addConsent(UUID userId,
                            //  ConsentType consentType //c'est plus maintenant enum mais le type de consentement
                              String consentTypeName
    ){
        if(!this.userRepository.existsById(userId)){
            return false;
        }
        ConsentType consentType=this.consentTypeRepository.findByName(consentTypeName);
        if(consentType==null){
            return false;
        }
        if(!consentType.isActive()){
            System.out.println("Consent type is not active");
            return  false;
        }
        consentType.setNbr(consentType.getNbr()+1);
        this.consentTypeRepository.save(consentType);
        User user = userRepository.findById(userId).orElseThrow();
        boolean exists = user.getConsentList().stream()
                .anyMatch(c -> c.getConsentType() == consentType);
        if (exists) return false;
        Consent consent = new Consent();
        consent.setUser(user);
        consent.setConsentType(consentType);
        user.getConsentList().add(consent);
        this.consentRepository.save(consent);
        this.userRepository.save(user);
        return true;
    }
    @Override
    @Transactional
    public boolean deleteConsent(UUID userId,
                                 //ConsentType consentType
                                 String consentTypeName){
        if(!this.userRepository.existsById(userId) || this.consentTypeRepository.findByName(consentTypeName) == null
        ){
            return false;
        }
        ConsentType consentType=this.consentTypeRepository.findByName(consentTypeName);
        if(this.consentRepository.findByUserIdAndConsentType(userId, consentType)!=null
        || this.consentRepository.findByUserIdAndConsentType(userId, consentType)==null
        ){
            return false;
        }
        consentType.setNbr(consentType.getNbr()-1);
        this.consentTypeRepository.save(consentType);
        Consent consent = this.consentRepository.findByUserIdAndConsentType(userId, consentType);
        consent.setOk(false);
        consent.setRevokedAt(LocalDateTime.now());
        this.consentRepository.save(consent);
        return true;
    }
    @Override
    public List<ConsentDto> getConsentsByUserId(UUID userId){
        if(!this.userRepository.existsById(userId)){
            return List.of();
        }
        List<Consent> consents = consentRepository.findAllByUserId(userId);
        return consents.stream()
                .map(ConsentDto::fromEntity)
                .toList();
    }
}
