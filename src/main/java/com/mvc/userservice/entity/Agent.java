package com.mvc.userservice.entity;

import com.mvc.userservice.enums.UserRole;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("AGENT")
public class Agent extends User{
    public Agent(){
        super();
        this.setRole(UserRole.Agent);
    }
}
