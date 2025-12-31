package com.mvc.userservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clients")
@Data
public class Client extends User{
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Account> accounts=new ArrayList<>();
    @OneToOne(mappedBy = "client",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    @JoinColumn(name = "crypto_wallet_id", referencedColumnName = "id")
    private CryptoWallet cryptoWallet;
}
