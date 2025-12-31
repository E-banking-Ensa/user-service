package com.mvc.userservice.dto;

import com.mvc.userservice.entity.Consent;
import com.mvc.userservice.entity.KycDocument;
import com.mvc.userservice.enums.KycDocumentStatus;
import com.mvc.userservice.enums.KycDocumentType;

import java.time.LocalDateTime;
import java.util.UUID;

public record KycDocumentResponseDto(UUID id,
                                     KycDocumentType documentType,
                                     KycDocumentStatus status,
                                     String pathToDocument,
                                     LocalDateTime uploadedAt,
                                     LocalDateTime reviewedAt,
                                     String reviewComment) {
    public static KycDocumentResponseDto fromEntity(KycDocument kycDocument){
        return new KycDocumentResponseDto(
                kycDocument.getId(),
                kycDocument.getDocumentType(),
                kycDocument.getStatus(),
                kycDocument.getPathToDocument(),
                kycDocument.getUploadedAt(),
                kycDocument.getReviewedAt(),
                kycDocument.getReviewComment()
        );
    }
}
