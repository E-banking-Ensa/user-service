package com.mvc.userservice.entity;

import com.mvc.userservice.enums.Status;
import com.mvc.userservice.enums.UserRole;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@DiscriminatorValue("AGENT")
@Data
public class Agent extends User{

    public Agent(){
        super();
        this.setRole(UserRole.Agent);
    }
}
