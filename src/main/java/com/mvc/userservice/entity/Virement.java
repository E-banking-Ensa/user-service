package com.mvc.userservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import com.mvc.userservice.enums.VirementType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Virement extends  Transaction{
    @ManyToOne
    private Account Source;
    @ManyToOne
    private Account Destination;
    private String Motife;
    @Enumerated(EnumType.STRING)
    private VirementType typeVirement;
}
