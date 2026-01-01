package com.mvc.userservice.entity;

import com.mvc.userservice.enums.UserRole;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;

@Entity
@DiscriminatorValue("AGENT")
//@AllArgsConstructor
public class Agent extends User{
    public Agent(){
        super();
        this.setRole(UserRole.Agent);
    }
}
