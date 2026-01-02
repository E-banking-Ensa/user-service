package com.mvc.userservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mvc.userservice.enums.KycStatus;
import com.mvc.userservice.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("CLIENT")
@Data
public class Client extends User{
    @Enumerated(EnumType.STRING)
    private KycStatus kycStatus=KycStatus.PENDING;
    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Consent> consentList=new ArrayList<>();
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<KycDocument> kycDocuments = new ArrayList<>();
    public Client(){
        super();
        this.setRole(UserRole.Client);
    }
}
