package com.mvc.userservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("CLIENT")
@Data
public class Client extends User{
    @OneToMany(mappedBy = "client",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnore
    private List<Account> accounts=new ArrayList<>();
    @OneToOne(mappedBy = "client",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    @JoinColumn(name = "crypto_wallet_id", referencedColumnName = "id")
    private CryptoWallet cryptoWallet;
}
