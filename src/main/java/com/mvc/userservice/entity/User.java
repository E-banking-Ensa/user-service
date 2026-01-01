package com.mvc.userservice.entity;

import com.mvc.userservice.enums.KycStatus;
import com.mvc.userservice.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="users")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false , unique = true)
    private UUID keycloakId;// ← UUID immuable venant de Keycloak (le "sub"), c'est a dire c'est la seule laison avec keycloak
    @Column(unique = true, nullable = false)
    private String username;
    @Column(name = "role")
    private UserRole role;//c'est le role duplique a nuevau dans notre base
    @Column(unique = true, nullable = false)
    private String email;
    private String phoneNumber;
    private String fullName;
    private String adresse;
    private int age;
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt=LocalDateTime.now();
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updateAt;
    @Column(nullable = false, updatable = true)
    private boolean enabled=false;//mais apres on verra s'il faut la changer en false par edfaut
}
