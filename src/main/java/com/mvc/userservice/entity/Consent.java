package com.mvc.userservice.entity;

//import com.mvc.userservice.enums.ConsentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "consents")

@EntityListeners(AuditingEntityListener.class)
public class Consent {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id" )
    private Client client;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "consent_type_id", nullable = false)
    private ConsentType consentType;
    @Column(nullable = false)
    private boolean isOk=true;//si l'utulisateur donne son accept pour ce consent
    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime grantedAt;//la date de donner ce droit
    private LocalDateTime revokedAt;//la date ou il a retire ce droit

    //methode utule pour faire bien retirer le droit dans le service
    public void revoke(){
        this.isOk=false;
        this.revokedAt=LocalDateTime.now();
    }
}
