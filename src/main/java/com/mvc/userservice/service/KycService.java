package com.mvc.userservice.service;

import com.mvc.userservice.dto.KycDocumentResponseDto;
import com.mvc.userservice.entity.KycDocument;
import com.mvc.userservice.enums.KycDocumentStatus;
import com.mvc.userservice.enums.KycDocumentType;
import com.mvc.userservice.enums.KycStatus;
import com.mvc.userservice.repository.KycDocumentRepository;
import com.mvc.userservice.repository.UserRepository;
import com.mvc.userservice.service.interfaces.IKycService;
import lombok.AllArgsConstructor;
import com.mvc.userservice.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class KycService implements IKycService {
    private final KycDocumentRepository kycDocumentRepository;
    private final UserRepository userRepository;
    @Override
    @Transactional
    public boolean submitKeycDocument(UUID userId, KycDocumentType type, MultipartFile file){
        if(file.isEmpty()){
            return false;
        }
        if(!this.userRepository.existsById(userId)){
            return false;
        }
        User user=this.userRepository.findById(userId).orElseThrow();
        KycDocument document=new KycDocument();
        document.setUser(user);
        document.setDocumentType(type);
        document.setPathToDocument("/path/to/document/"+file.getOriginalFilename());//par la suite on va faire le vrai stockage
        this.kycDocumentRepository.save(document);
        user.getKycDocuments().add(document);
        this.userRepository.save(user);
        this.updateUserKycStatus(user);
        return true;
    }
    @Override
    @Transactional
    public boolean verifyKycDocument(UUID documentId){
        if(!this.kycDocumentRepository.existsById(documentId)){
            return false;
        }
        KycDocument document=this.kycDocumentRepository.findById(documentId).orElseThrow();
        document.setStatus(KycDocumentStatus.APPROVED);
        this.kycDocumentRepository.save(document);
        this.updateUserKycStatus(document.getUser());
        return true;
    }
    @Override
    @Transactional
    public boolean rejectKycDocument(UUID documentId, String reason){
        if(!this.kycDocumentRepository.existsById(documentId)){
            return false;
        }
        KycDocument document=this.kycDocumentRepository.findById(documentId).orElseThrow();
        document.setStatus(KycDocumentStatus.REJECTED);
        document.setReviewComment(reason);
        this.kycDocumentRepository.save(document);
        this.updateUserKycStatus(document.getUser());
        return true;
    }
    @Override
    public List<KycDocumentResponseDto> getKycDocumentsByUserId(UUID userId){
        if(!this.userRepository.existsById(userId)){
            return List.of();
        }
        List<KycDocument> documents=this.kycDocumentRepository.findAllByUserId(userId);
        return documents.stream().map(doc -> new KycDocumentResponseDto(
                doc.getId(),
                doc.getDocumentType(),
                doc.getStatus(),
                doc.getPathToDocument(),
                doc.getUploadedAt(),
                doc.getReviewedAt(),
                doc.getReviewComment()
        )).toList();
    }
    private void updateUserKycStatus(User user){
        List<KycDocument> documents=user.getKycDocuments();
        if(!documents.isEmpty()){
            boolean allApproved=documents.stream()
                    .allMatch(doc -> doc.getStatus() == KycDocumentStatus.APPROVED);
            boolean hasRejected=documents.stream()
                    .anyMatch(doc -> doc.getStatus() == KycDocumentStatus.REJECTED);
            if(hasRejected){
                user.setKycStatus(KycStatus.REJECTED);
            }else
                if(allApproved) {
                    user.setKycStatus(KycStatus.APPROVED);
                }else{
                    user.setKycStatus(KycStatus.IN_REVIEW);
                }
            this.userRepository.save(user);
        }
    }
}
