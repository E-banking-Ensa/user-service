package com.mvc.userservice.config;

import com.mvc.userservice.entity.Client;
import com.mvc.userservice.entity.User;
import com.mvc.userservice.enums.KycStatus;
import com.mvc.userservice.enums.UserRole;
import com.mvc.userservice.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@Profile("dev")
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    public DataInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) {
            System.out.println("⚠️ Utilisateurs déjà présents — initialisation annulée.");
            return;
        }

        System.out.println("🚀 Initialisation des utilisateurs de test...");

        createUser(UUID.randomUUID(), "client1", "client1@ebank.com", "+33611111111", UserRole.Client, KycStatus.PENDING);
        createUser(UUID.randomUUID(), "agent1", "agent1@ebank.com", "+33622222222", UserRole.Client, KycStatus.APPROVED);
        createUser(UUID.randomUUID(), "admin1", "admin1@ebank.com", "+33633333333", UserRole.Client, KycStatus.APPROVED);

        System.out.println("✅ 3 utilisateurs créés avec succès : client1, agent1, admin1");
    }
    private void createUser(UUID keycloakId, String username, String email, String phone, UserRole role, KycStatus kycStatus) {
        System.out.println("   - Création de l'utilisateur : " + username);
        Client user = new Client();
        System.out.println("     > Génération d'un nouvel ID UUID pour l'utilisateur.");
       // user.setId(UUID.randomUUID());
        System.out.println("     > Attribution des propriétés de l'utilisateur.");
        user.setKeycloakId(keycloakId);
        System.out.println("     > Keycloak ID défini sur : " + keycloakId);
        user.setUsername(username);
        System.out.println("     > Nom d'utilisateur défini sur : " + username);
        user.setEmail(email);
        System.out.println("     > Email défini sur : " + email);
        // user.setPhone(phone);
        user.setRole(role);
        //user.setVersion(null); // au lieu de 0L
        user.setKycStatus(kycStatus);
        System.out.println("     > Statut KYC défini sur : " + kycStatus);
        user.setEnabled(true);
        System.out.println();
//        user.setCreatedDate(LocalDateTime.now());
//        user.setLastModifiedDate(LocalDateTime.now());
        userRepository.save(user);
        if (userRepository.save(user) != null) {
            System.out.println("     > Utilisateur sauvegardé avec succès dans la base de données.");
            System.out.println("   - Utilisateur créé : " + username);
        }
        else{
            System.out.println("     > Échec de la sauvegarde de l'utilisateur dans la base de données.");
        }
    }
}