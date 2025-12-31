package com.mvc.userservice.entity;

import com.mvc.userservice.enums.KycDocumentType;
import com.mvc.userservice.enums.KycDocumentStatus;
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
@Table(name = "kyc_documents")
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class KycDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private KycDocumentType documentType;// la nature de documenet , piece de CIN , photo , ...
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private KycDocumentStatus status= KycDocumentStatus.SUBMITTED;//l'etat de ce document : soumis , en cours de verification , approuve , refuse
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(nullable = false)
    private String pathToDocument;//c'est le chemin vers le document , peut etre localement
    //bien stcke dans un cloud ...
    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime uploadedAt;//date ou on a faite uplod pour ce document
    private LocalDateTime reviewedAt;//date de verification par la banque
    private String reviewComment;//un telle commentaire de la part de la banque
}
