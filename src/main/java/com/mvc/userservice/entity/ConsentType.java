package com.mvc.userservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "consent_types")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
public class ConsentType {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable=false,unique=true)
    private String code;
    @Column(nullable=false,unique=true)
    private String name;
    private boolean isActive=false;//indique par admin s'il est actve et propose chez les clients ou non
    @CreatedDate
    @Column(nullable=false,updatable=false)
    private LocalDateTime createdAt=LocalDateTime.now();
    @LastModifiedDate
    @Column(nullable=false)
    private LocalDateTime updatedAt;
    private int nbr=0;//le nombre de fois que ce consent a ete donne par les utilisateurs
}

