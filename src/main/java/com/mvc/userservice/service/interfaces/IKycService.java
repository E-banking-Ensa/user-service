package com.mvc.userservice.service.interfaces;

import com.mvc.userservice.dto.KycDocumentResponseDto;
import com.mvc.userservice.enums.KycDocumentType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface IKycService {
    boolean submitKeycDocument(UUID userId, KycDocumentType type, MultipartFile file);
    boolean verifyKycDocument(UUID documentId);
    boolean rejectKycDocument(UUID documentId, String reason);
    List<KycDocumentResponseDto> getKycDocumentsByUserId(UUID userId);
}
