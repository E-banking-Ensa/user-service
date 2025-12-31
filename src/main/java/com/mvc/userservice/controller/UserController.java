package com.mvc.userservice.controller;

import com.mvc.userservice.dto.*;
import com.mvc.userservice.entity.ConsentType;
import com.mvc.userservice.enums.KycDocumentType;
import com.mvc.userservice.service.interfaces.IConsentService;
import com.mvc.userservice.service.interfaces.IConsentTypeService;
import com.mvc.userservice.service.interfaces.IKycService;
import com.mvc.userservice.service.interfaces.IUserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
//@NoArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final IUserService userService;
    private final IKycService kycService;
    private final IConsentService consentService;
    private final IConsentTypeService consentTypeService;

//    Profil user=======v=======================================================================================

    @PostMapping("/internal/sync")
    @PreAuthorize("hasRole('Admin')") //adopter pour la secrirte inter service
    public ResponseEntity<UserResponseDto> syncUser(
        //    @Valid
            @RequestBody CreateUserRequestDto dto){
        System.out.println("Syncing user with Keycloak ID: " + dto.keycloakId());
        return ResponseEntity.ok(this.userService.createUser(dto));
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('Admin') or #userId == authentication.principal.attributes.sub")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(userService.getUser(userId)); // il me reste pour l'implimetaion
    }

    @GetMapping("/allClients")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<List<UserResponseDto>> getAllClients() {
        return ResponseEntity.ok(userService.getAllClients());
    }

    @PutMapping("/{userId}")
    @PreAuthorize("#userId == authentication.principal.attributes.sub")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable UUID userId,@Valid @RequestBody UpdateUserRequestDto dto) {
        return ResponseEntity.ok(userService.updateUser(userId, dto));
    }

//    RGPD  ==============================================================================================

    //RECUPERER TOUTES LES RGPD D'UN USER
    @GetMapping("/{userId}/consents")
    @PreAuthorize("hasRole('Admin') or #userId == authentication.principal.attributes.sub")
    public ResponseEntity<List<ConsentDto>> getConsents(@PathVariable UUID userId) {
        return ResponseEntity.ok(consentService.getConsentsByUserId(userId));
    }

    //AJOUTER UN CONSENTEMENT RGPD POUR UN USER
    @PostMapping("/{userId}/consents")
    @PreAuthorize("#userId == authentication.principal.attributes.sub")
    public ResponseEntity<Void> addConsent(
            @PathVariable UUID userId,@RequestParam String consentType){
        consentService.addConsent(userId, consentType);
        return ResponseEntity.ok().build();
    }

    //REVOQUER UN CONSENTEMENT RGPD POUR UN USER,mais
    // c'est pas une suppression c'est une sorte de descarivation
    @DeleteMapping("/{userId}/consents/{consentType}")
    @PreAuthorize("#userId == authentication.principal.attributes.sub")
    public ResponseEntity<Void> revokeConsent(@PathVariable UUID userId,@PathVariable String consentType){
        consentService.deleteConsent(userId,consentType);
        return ResponseEntity.ok().build();
    }

//    KYC==============================================================================================

    //UPLOAD D'UN DOCUMENT KYC POUR UN USER
    @PostMapping("/{userId}/kyc/documents")
    @PreAuthorize("#userId == authentication.principal.attributes.sub")
    public ResponseEntity<Void> uploadKycDocument(
            @PathVariable UUID userId,
            @RequestParam KycDocumentType documentType,
            @RequestParam("file") MultipartFile file) {
        kycService.submitKeycDocument(userId, documentType, file);
        return ResponseEntity.ok().build();
    }

    //RECUPERER TOUS LES DOCUMENTS KYC D'UN USER
    @GetMapping("/{userId}/kyc/documents")
    @PreAuthorize("hasRole('Admin') or #userId == authentication.principal.attributes.sub")
    public ResponseEntity<List<KycDocumentResponseDto>> getKycDocuments(@PathVariable UUID userId) {
        return ResponseEntity.ok(kycService.getKycDocumentsByUserId(userId));
    }

    //APPROUVER UN DOCUMENT KYC , MAIS C'EST AUTORISE PAR ADMIN OU AGENT
    @PutMapping("/kyc/documents/{documentId}/approve")
    @PreAuthorize("hasRole('Admin') or hasRole('Agent')")
    public ResponseEntity<Void> approveDocument(@PathVariable UUID documentId) {
        kycService.verifyKycDocument(documentId);
        return ResponseEntity.ok().build();
    }

    //REJETER UN DOCUMENT KYC , MAIS C'EST AUTORISE PAR ADMIN OU AGENT
    @PutMapping("/kyc/documents/{documentId}/reject")
    @PreAuthorize("hasRole('Admin') or hasRole('Agent')")
    public ResponseEntity<Void> rejectDocument(
            @PathVariable UUID documentId,
            @RequestParam String reason) {
        kycService.rejectKycDocument(documentId, reason);
        return ResponseEntity.ok().build();
    }

// =================================== ADMIN : GESTION DES TYPES DE CONSENTEMENT ===================================

    @GetMapping("/admin/consent-types")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<List<ConsentTypeDto>> getAllConsentTypes() {
        return ResponseEntity.ok(this.consentTypeService.getAllConsentTypes());
    }

    @PostMapping("/admin/consent-types")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<ConsentTypeDto> createConsentType(@Valid @RequestBody ConsentTypeRequest request) {
        ConsentType created = consentTypeService.create(request);
        return ResponseEntity.ok(ConsentTypeDto.fromEntity(created));
    }

    @PutMapping("/admin/consent-types/{typeId}/activate")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Void> activateConsentType(@PathVariable UUID typeId) {
        if (consentTypeService.activateConsentType(typeId)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/admin/consent-types/{typeId}/deactivate")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Void> deactivateConsentType(@PathVariable UUID typeId) {
        if (consentTypeService.deactivateConsentType(typeId)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    // Optionnel : suppression douce si aucun utilisateur ne l'utilise
    @DeleteMapping("/admin/consent-types/{typeId}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Void> deleteConsentType(@PathVariable UUID typeId) {
        consentTypeService.deleteConsentType(typeId);
        return ResponseEntity.ok().build();
    }
}
