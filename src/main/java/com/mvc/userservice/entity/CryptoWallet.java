package com.mvc.userservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "crypto_wallets")
@Data
public class CryptoWallet {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true,nullable = false)
    private String walletAddress;
    private Double balanceVTC;
    private Double balanceETH;
    @OneToOne(mappedBy="cryptoWallet",fetch = FetchType.LAZY)
    private Client client;
}
