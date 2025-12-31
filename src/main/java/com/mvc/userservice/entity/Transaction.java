package com.mvc.userservice.entity;

import com.mvc.userservice.enums.TypeTransaction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Inheritance(strategy = jakarta.persistence.InheritanceType.JOINED)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Double Montant;
    private LocalDateTime date=LocalDateTime.now();
    @Enumerated(EnumType.STRING)
    private TypeTransaction typeTransaction;
}
