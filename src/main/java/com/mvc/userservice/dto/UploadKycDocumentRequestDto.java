package com.mvc.userservice.dto;

import com.mvc.userservice.enums.KycDocumentType;
import org.springframework.web.multipart.MultipartFile;

public record UploadKycDocumentRequestDto(KycDocumentType documentType,
                                          MultipartFile file) {
}
