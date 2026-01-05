package com.mvc.userservice.service.interfaces;

import com.mvc.userservice.dto.CreateUserRequestDto;
import com.mvc.userservice.dto.UpdateUserRequestDto;
import com.mvc.userservice.dto.UserResponseDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserService {
    UserResponseDto createUser(CreateUserRequestDto dto);//pour creation d'un utilisateur
    UserResponseDto updateUser(UUID userId, UpdateUserRequestDto dto);//pour la mise a jour d'un utilisateur
    UserResponseDto getUser(UUID userId);
    List<UserResponseDto> getAllClients();
    List<UserResponseDto> getAllAgents();
    boolean deleteUser(UUID userId);
    
    // Méthodes internes pour la communication inter-services
    boolean userExists(UUID userId);
    UserResponseDto getOrCreateUser(CreateUserRequestDto dto, UUID userId);
    Optional<UserResponseDto> findUserById(UUID userId);
    UserResponseDto getUserByEmail(String email);
   // List<UserResponseDto> getAll
//    void updateConsent(UUID userId, ConsentType type, boolean granted);//pour la mise a jour des droits d'un utilisateur
//    void updateKycStatus(UUID userId, String kycStatus);//pour la mise a jour du statut kyc d'un utilisateur
//    void uploadDocument(UUID userId, String documentType, String documentUrl);//pour l'upload des documents d'un utilisateur
}
