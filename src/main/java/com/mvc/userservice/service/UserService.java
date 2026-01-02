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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
        if (dto.role() == UserRole.Client) {
            user = new Client();
            // Le constructeur de Client force déjà role = CLIENT et kycStatus = PENDING
        }
        else if (dto.role() == UserRole.Agent) {
            user = new Agent();
        }
        else if (dto.role() == UserRole.Admin) {
            user = new Admin();
        }
        else {
            throw new IllegalArgumentException("Rôle inconnu");
        }
        user.setKeycloakId(dto.keycloakId());
        user.setUsername(dto.username());
        user.setEmail(dto.email());
        user.setRole(dto.role());
        user.setPhoneNumber(dto.phoneNumber());
        user.setFullName(dto.fullName());
        user.setAdresse(dto.address());
        this.userRepository.save(user);
        return UserResponseDto.fromEntity(user);
    }
    @Override
    public UserResponseDto updateUser(UUID userId, UpdateUserRequestDto dto){
        if(!this.userRepository.existsById(userId)){
            throw new IllegalArgumentException("User not found");
        }
        User user = this.userRepository.findById(userId).orElse(null);
        if(dto.fullName()!=null){
            user.setFullName(dto.fullName());
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
}
