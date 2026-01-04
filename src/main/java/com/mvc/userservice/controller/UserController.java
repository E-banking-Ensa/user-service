package com.mvc.userservice.controller;

import com.mvc.userservice.dto.*;
import com.mvc.userservice.entity.ConsentType;
import com.mvc.userservice.enums.KycDocumentType;
import com.mvc.userservice.service.interfaces.*;
//import com.mvc.userservice.test.KafkaTestProducer;
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
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/v1/users")
public class UserController {
    private final IUserService userService;
    private final IKycService kycService;
    private final IConsentService consentService;
    private final IConsentTypeService consentTypeService;
    //private final KafkaTestProducer kafkaTestProducer;
    private final IClientService clientService;
    private final IAgentService agentService;
    private final IDashbordService dashbordService;


//    Profil user=======v=======================================================================================

    @GetMapping("/admin/dash")
    public ResponseEntity<DashbordAdmin> getAdminDashbord(){
        System.out.println("getAdminDashbord");
        return ResponseEntity.ok(this.dashbordService.getDashbordAdmin());
    }

    @PutMapping("/clients/{clientId}/desactivate")//descativer client
    public ResponseEntity<Void> desactivateClient(@PathVariable UUID clientId){
        System.out.println("suspendre du client avec id :"+clientId);
        this.clientService.deactivate(clientId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/clients/{clientId}/activate")//activer client
    public ResponseEntity<Void> activateClient(@PathVariable UUID clientId){
        this.clientService.activate(clientId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/internal/sync")
   // @PreAuthorize("hasRole('Admin') or hasRole('Agent')") //adopter pour la secrirte inter service
    public ResponseEntity<UserResponseDto> syncUser(@RequestBody CreateUserRequestDto dto){
//        System.out.println("Syncing user with Keycloak ID: " + dto.keycloakId());
        System.out.println("Syncing user with id: ");
        System.out.println("Syncing user with username: " + dto.username());
        System.out.println("Syncing user with email: " + dto.email());
        System.out.println("Syncing user with role: " + dto.role());
        System.out.println("Syncing user with firstName: " + dto.firstName());
        System.out.println("Syncing user with lastName: " + dto.lastName());
        System.out.println("Syncing user with phoneNumber: " + dto.phoneNumber());
        System.out.println("Syncing user with age: " + dto.age());
        System.out.println("Syncing user with address: " + dto.adresse());
        System.out.println("User synced successfully.");
        return ResponseEntity.ok(this.userService.createUser(dto));
    }

    @GetMapping("/{userId}")//retourner un tel user
   // @PreAuthorize("hasRole('Admin') or hasRole('Agent') or #userId == authentication.principal.attributes.sub")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(userService.getUser(userId)); // il me reste pour l'implimetaion
    }

    @GetMapping("/client/{clientId}")//retourner un tel client precimenet
   // @PreAuthorize("hasRole('Admin') or hasRole('Agent') or #clientId == authentication.principal.attributes.sub")
    public ResponseEntity<ClientDto> getClientById(@PathVariable UUID clientId) {
        return ResponseEntity.ok(this.clientService.getClientById(clientId));
    }

    @GetMapping("/allClients")//retourner tous les clients
   // @PreAuthorize("hasRole('Admin') or hasRole('Agent')")
    public List<ClientDto> getAllClients() {
        System.out.println("Fetching all clients");
        return this.clientService.getAllClients();
    }

    @PutMapping("/{userId}")//faire update d'un tel user
    //@PreAuthorize("#userId == authentication.principal.attributes.sub")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable UUID userId,@Valid @RequestBody UpdateUserRequestDto dto) {
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
    public ResponseEntity<Void> addConsent(@PathVariable UUID userId,@RequestParam String consentType){
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
    @PreAuthorize(" #userId == authentication.principal.attributes.sub")
    public ResponseEntity<Void> uploadKycDocument(
            @PathVariable UUID userId, @RequestParam KycDocumentType documentType,
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
    public ResponseEntity<Void> rejectDocument(@PathVariable UUID documentId, @RequestParam String reason) {
        kycService.rejectKycDocument(documentId, reason);
        return ResponseEntity.ok().build();
    }

// =================================== ADMIN : GESTION DES TYPES DE CONSENTEMENT ===================================

    //recuperer tous les types de consentement
    @GetMapping("/admin/consent-types")
    //@PreAuthorize("hasRole('Admin')")
    public ResponseEntity<List<ConsentTypeDto>> getAllConsentTypes() {
        System.out.println("Getting all consent types");
        return ResponseEntity.ok(this.consentTypeService.getAllConsentTypes());
    }

    //creer un type de consentement
    @PostMapping("/admin/consent-types")
    //@PreAuthorize("hasRole('Admin')")
    public ResponseEntity<ConsentTypeDto> createConsentType(@Valid @RequestBody ConsentTypeRequest request) {
        System.out.println("Creating consent type: " + request.name());
        System.out.println("Description: " + request.code());
        ConsentType created = consentTypeService.create(request);
        return ResponseEntity.ok(ConsentTypeDto.fromEntity(created));
    }

    //activer un type de consentement
    @PutMapping("/admin/consent-types/{typeId}/activate")
    //@PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Void> activateConsentType(@PathVariable UUID typeId) {
        if (consentTypeService.activateConsentType(typeId)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    //desactiver un type de consentement
    @PutMapping("/admin/consent-types/{typeId}/deactivate")
    //@PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Void> deactivateConsentType(@PathVariable UUID typeId) {
        if (consentTypeService.deactivateConsentType(typeId)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    // Optionnel : suppression douce si aucun utilisateur ne l'utilise
    @DeleteMapping("/admin/consent-types/{typeId}")
    //@PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Void> deleteConsentType(@PathVariable UUID typeId) {
        consentTypeService.deleteConsentType(typeId);
        return ResponseEntity.ok().build();
    }

//===AFFICHAGE DES AGENTS ==================================================================================================

    //RECUPERER UN TEL DASHBORD D'UN AGENT PAR SON USERNAME
    @GetMapping("/agents/{username}/dashbord")
    public ResponseEntity<DashbordAgent> getDashbordAgent(@PathVariable String username) {
        System.out.println("Fetching dashbord for agent with ID: " + username);
        return ResponseEntity.ok(this.dashbordService.getDashbordAgent(username));
    }

    //recuperer tous les agents
    @GetMapping("/allAgents")
    //@PreAuthorize("hasRole('Admin')")
    public ResponseEntity<List<AgentDto>> getAllAgents() {
        System.out.println("Fetching all agents");
        return ResponseEntity.ok(this.agentService.getAllAgents());
    }

    //SUPPRIMER UN TEL AGENT PAR SON ID
    @DeleteMapping("/agents/{agentId}/delete" )
    //@PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Void> deleteAgent(@PathVariable UUID agentId){
        userService.deleteUser(agentId);
        return ResponseEntity.ok().build();
    }

    //ACTIVER UN TEL AGENT PAR SON ID
    @PutMapping("/agents/{agentId}/activate")
  //  @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Void> activateAgent(@PathVariable UUID agentId){
        this.agentService.activate(agentId);
        return ResponseEntity.ok().build();
    }

    //DESACTIVER UN TEL AGENT PAR SON ID
    @PutMapping("/agents/{agentId}/desactivate")
   // @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Void> deactivateAgent(@PathVariable UUID agentId){
        this.agentService.deactivate(agentId);
        return ResponseEntity.ok().build();
    }

    //BLOQUER UN TEL AGENT PAR SON ID
    @PutMapping("/agents/{agentId}/block")
   // @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Void> blockAgent(@PathVariable UUID agentId){
        System.out.println("Blocking agent with ID: " + agentId);

        this.agentService.block(agentId);
        return ResponseEntity.ok().build();
    }

    //SUPPRIMER UN TEL AGENT PAR SON ID
    @PutMapping("/agents/{agentId}/delete")
   // @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Void> deleteAgentPut(@PathVariable UUID agentId){
        System.out.println("Deleting agent with ID: " + agentId);
        this.agentService.delete(agentId);
        return ResponseEntity.ok().build();
    }

//
//    @PostMapping("/create/agent")
//    @PreAuthorize("hasRole('Admin')")
//    public

//====TEST DE KAFKKA=============================================================================================

//    @GetMapping("/test/kafka-client")
//    @PreAuthorize("hasRole('Admin')")
//    public ResponseEntity<String> testKafkaClient() {
//        kafkaTestProducer.sendTestClientEvent();
//        return ResponseEntity.ok("Événement Client envoyé sur Kafka ! Vérifiez les logs et pgAdmin.");
//    }
//
//    @GetMapping("/test/kafka-agent")
//    @PreAuthorize("hasRole('Admin')")
//    public ResponseEntity<String> testKafkaAgent() {
//        kafkaTestProducer.sendTestAgentEvent();
//        return ResponseEntity.ok("Événement Agent envoyé sur Kafka ! Vérifiez les logs et pgAdmin.");
//    }
}
