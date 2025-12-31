# Aperçu et Fonctionnement du Microservice User-Service

## Introduction
Le microservice **user-service** est responsable de la gestion des profils utilisateurs, des vérifications KYC (Know Your Customer) et des consentements RGPD.  
Il s'intègre dans l'architecture E-Banking 3.0 décrite dans le cahier des charges :
- Base de données : PostgreSQL pour les profils et consentements.
- Interopérabilité : Appels REST internes vers d'autres microservices via API Gateway.
- Sécurité : OAuth2 avec Keycloak pour les rôles (client, agent, admin).
- Événements : Publication vers Kafka (ex. : user.created, kyc.approved).
- Stockage fichiers : MinIO/S3 pour les documents KYC (à implémenter).

**Objectifs métiers** :
- Permettre aux clients de créer et gérer leur profil.
- Gérer les consentements RGPD (donner/retirer).
- Gérer les documents KYC (soumettre, vérifier, rejeter).
- Assurer la conformité RGPD (traçabilité, révocation) et KYC (anti-fraude).

## Architecture Générale
- **Entités** : User, Consent, KycDocument (avec enums pour types/status).
- **Repositories** : UserRepository, ConsentRepository, KycDocumentRepository (héritant JpaRepository pour CRUD).
- **Services** : UserService (profil), ConsentService (RGPD), KycService (KYC).
- **DTOs** : Utilisés pour les entrées/sorties API (records pour immutabilité, avec `fromEntity()` pour mapping manuel).
- **Flux principal** : Requête API → Validation → Service → Repository → Base → Mapping DTO → Réponse.

### Diagramme de Flux Haut Niveau