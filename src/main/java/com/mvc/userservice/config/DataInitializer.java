package com.mvc.userservice.config;

import com.mvc.userservice.entity.Client;
import com.mvc.userservice.entity.ConsentType;
import com.mvc.userservice.entity.User;
import com.mvc.userservice.enums.KycStatus;
import com.mvc.userservice.enums.Status;
import com.mvc.userservice.enums.UserRole;
import com.mvc.userservice.repository.ConsentTypeRepository;
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
    private final ConsentTypeRepository consentTypeRepository;

    public DataInitializer(UserRepository userRepository, ConsentTypeRepository consentTypeRepository) {
        this.userRepository = userRepository;
        this.consentTypeRepository = consentTypeRepository;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) {
            System.out.println("⚠️ Utilisateurs déjà présents — initialisation annulée.");
            return;
        }

        createConsent();

        System.out.println("🚀 Initialisation des utilisateurs de test...");

        // Création de deux clients avec tous les attributs
        createClient(
                "client1", "client1@ebank.com", "+33611111111",
                "Jean", "Dupont", "Rue de Paris 10, Paris", 30,
                KycStatus.PENDING, Status.ACTIVE
        );

        createClient(
                "client2", "client2@ebank.com", "+33622222222",
                "Alice", "Martin", "Rue de Lyon 5, Lyon", 28,
                KycStatus.APPROVED, Status.ACTIVE
        );

        System.out.println("✅ 2 clients créés avec succès : client1, client2");
    }

    private void createConsent() {
        System.out.println("   - Création des consentements par défaut pour les utilisateurs.");
        ConsentType consentType1 = new ConsentType();
        consentType1.setCode("MARKETING_EMAILS");
        consentType1.setName("Marketing Emails");
        consentType1.setActive(true);
        consentType1.setNbr(0);
        consentTypeRepository.save(consentType1);
        System.out.println("     > Consentement sauvegardé dans la base de données.");
    }

    private void createClient(String username, String email, String phone,
                              String firstName, String lastName, String adresse, int age,
                              KycStatus kycStatus, Status status) {

        Client client = new Client();
        client.setUsername(username);
        client.setEmail(email);
        client.setPhoneNumber(phone);
        client.setFirstName(firstName);
        client.setLastName(lastName);
        client.setAdresse(adresse);
        client.setAge(age);
        client.setKycStatus(kycStatus);
        client.setStatus(status);

        userRepository.save(client);

        System.out.println("   - Client créé : " + username + " (" + firstName + " " + lastName + ")");
    }
}



//@Component
//@Profile("dev")
//public class DataInitializer implements CommandLineRunner {
//
//    private final UserRepository userRepository;
//    private final ConsentTypeRepository consentTypeRepository;
//
//    public DataInitializer(UserRepository userRepository, ConsentTypeRepository consentTypeRepository) {
//        this.userRepository = userRepository;
//        this.consentTypeRepository = consentTypeRepository;
//    }
//
//    @Override
//    public void run(String... args) {
//        if (userRepository.count() > 0) {
//            createConsent();
//            System.out.println("⚠️ Utilisateurs déjà présents — initialisation annulée.");
//            return;
//        }
//
//        createConsent();
//        System.out.println("🚀 Initialisation des utilisateurs de test...");
//
//        createUser(UUID.randomUUID(), "client1", "client1@ebank.com", "+33611111111", UserRole.Client, KycStatus.PENDING);
//        createUser(UUID.randomUUID(), "agent1", "agent1@ebank.com", "+33622222222", UserRole.Client, KycStatus.APPROVED);
//        createUser(UUID.randomUUID(), "admin1", "admin1@ebank.com", "+33633333333", UserRole.Client, KycStatus.APPROVED);
//
//        System.out.println("✅ 3 utilisateurs créés avec succès : client1, agent1, admin1");
//    }
//    private void createConsent(){
//        System.out.println("   - Création des consentements par défaut pour les utilisateurs.");
//        // Implémentation de la création des consentements
//        ConsentType consentType1 = new ConsentType();
//        consentType1.setCode("MARKETING_EMAILS"); // Obligatoire
//        consentType1.setName("Marketing Emails");
//        consentType1.setActive(true);
//        consentType1.setNbr(0);
//        consentTypeRepository.save(consentType1);
//        System.out.println("     > Consentement sauvegardé dans la base de données.");
//
//        System.out.println("     > Consentement sauvegardé dans la base de données.");
//
//    }
//    private void createUser(UUID keycloakId, String username, String email, String phone, UserRole role, KycStatus kycStatus) {
//        System.out.println("   - Création de l'utilisateur : " + username);
//        Client user = new Client();
//        System.out.println("     > Génération d'un nouvel ID UUID pour l'utilisateur.");
//       // user.setId(UUID.randomUUID());
//        System.out.println("     > Attribution des propriétés de l'utilisateur.");
////        user.setKeycloakId(keycloakId);
//        System.out.println("     > Keycloak ID défini sur : " + keycloakId);
//        user.setUsername(username);
//        System.out.println("     > Nom d'utilisateur défini sur : " + username);
//        user.setEmail(email);
//        System.out.println("     > Email défini sur : " + email);
//        // user.setPhone(phone);
//        user.setRole(role);
//        //user.setVersion(null); // au lieu de 0L
//        user.setKycStatus(kycStatus);
//        System.out.println("     > Statut KYC défini sur : " + kycStatus);
//        user.setStatus(Status.ACTIVE);
//        System.out.println();
////        user.setCreatedDate(LocalDateTime.now());
////        user.setLastModifiedDate(LocalDateTime.now());
//        userRepository.save(user);
//        if (userRepository.save(user) != null) {
//            System.out.println("     > Utilisateur sauvegardé avec succès dans la base de données.");
//            System.out.println("   - Utilisateur créé : " + username);
//        }
//        else{
//            System.out.println("     > Échec de la sauvegarde de l'utilisateur dans la base de données.");
//        }
//    }
//}