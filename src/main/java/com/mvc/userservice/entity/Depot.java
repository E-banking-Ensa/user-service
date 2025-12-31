package com.mvc.userservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@Table(name = "depots")
public class Depot extends Transaction{
    @ManyToOne
    private Account account;
}
