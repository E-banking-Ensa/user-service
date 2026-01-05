package com.mvc.userservice.service;

import com.mvc.userservice.dto.CreateUserRequestDto;
import com.mvc.userservice.dto.UpdateUserRequestDto;
import com.mvc.userservice.dto.UserResponseDto;
import com.mvc.userservice.entity.Admin;
import com.mvc.userservice.entity.Agent;
import com.mvc.userservice.entity.Client;
import com.mvc.userservice.enums.UserRole;
import com.mvc.userservice.service.interfaces.IUserService;
import com.mvc.userservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import com.mvc.userservice.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class UserService implements IUserService {
    private final UserRepository userRepository;
    @Override
    public boolean deleteUser(UUID userId){
        if(!this.userRepository.existsById(userId)){
            return false;
        }
        this.userRepository.deleteById(userId);
        return true;
    }
    @Override
    public List<UserResponseDto> getAllClients() {
        List<User> users = userRepository.findAll();
        return users.stream()
                //filtrer par les users avec un role de CLIENT
                .filter(user -> user.getRole() == UserRole.Client)
                .map(UserResponseDto::fromEntity)
                .toList();
    }
    @Override
    public List<UserResponseDto> getAllAgents(){
        List<User> users = userRepository.findAll();
        return users.stream()
                .filter(user->user.getRole()== UserRole.Agent)
                .map(UserResponseDto::fromEntity)
                .toList();
    }
    @Override
    public UserResponseDto createUser(CreateUserRequestDto dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("Email déjà utilisé");
        }
        if (userRepository.existsByUsername(dto.username())) {
            throw new IllegalArgumentException("Username déjà utilisé");
        }
        User user ;
        if (dto.role().equals(UserRole.Client.name())) {
            user = new Client();
            // Le constructeur de Client force déjà role = CLIENT et kycStatus = PENDING
        }
        else if (dto.role().equals(UserRole.Agent.name())) {
            user = new Agent();
        }
        else if (dto.role().equals(UserRole.Admin.name())) {
            user = new Admin();
        }
        else {
            throw new IllegalArgumentException("Rôle inconnu");
        }
        user.setUsername(dto.username());
        user.setEmail(dto.email());
        user.setPhoneNumber(dto.phoneNumber());
        user.setAdresse(dto.adresse());
        user.setLastName(dto.lastName());
        user.setFirstName(dto.firstName());
        System.out.println("voila ici");
        this.userRepository.save(user);
        return UserResponseDto.fromEntity(user);
    }
    @Override
    public UserResponseDto updateUser(UUID userId, UpdateUserRequestDto dto){
        if(!this.userRepository.existsById(userId)){
            throw new IllegalArgumentException("User not found");
        }
        User user = this.userRepository.findById(userId).orElse(null);
        if(user==null){
            throw new IllegalArgumentException("User not found");
        }
        if(dto.email()!=null){
            user.setEmail(dto.email());
        }
        if(dto.firstName()!=null){
            user.setFirstName(dto.firstName());
        }
        if(dto.lastName()!=null){
            user.setLastName(dto.lastName());
        }
        if(dto.phoneNumber()!=null){
            user.setPhoneNumber(dto.phoneNumber());
        }
        if(dto.address()!=null){
            user.setAdresse(dto.address());
        }
        this.userRepository.save(user);
        return UserResponseDto.fromEntity(user);
    }
    @Override
    public UserResponseDto getUser(UUID userId){
        if(!this.userRepository.existsById(userId)){
            throw new IllegalArgumentException("User not found");
        }
        User user = this.userRepository.findById(userId).orElseThrow();
        return UserResponseDto.fromEntity(user);
    }

    @Override
    public boolean userExists(UUID userId) {
        return this.userRepository.existsById(userId);
    }

    @Override
    public Optional<UserResponseDto> findUserById(UUID userId) {
        return this.userRepository.findById(userId)
                .map(UserResponseDto::fromEntity);
    }

    @Override
    public UserResponseDto getOrCreateUser(CreateUserRequestDto dto, UUID userId) {
        // Vérifie si l'utilisateur existe déjà
        if (userId != null) {
            Optional<User> existingUser = this.userRepository.findById(userId);
            if (existingUser.isPresent()) {
                return UserResponseDto.fromEntity(existingUser.get());
            }
        }
        
        // Vérifie par email
        Optional<User> userByEmail = this.userRepository.findByEmail(dto.email());
        if (userByEmail.isPresent()) {
            return UserResponseDto.fromEntity(userByEmail.get());
        }
        
        // Créer un nouveau utilisateur
        return createUser(dto);
    }

    @Override
    public UserResponseDto getUserByEmail(String email) {
        User user = this.userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));
        return UserResponseDto.fromEntity(user);
    }
}
