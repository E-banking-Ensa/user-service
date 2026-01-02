package com.mvc.userservice.entity;

import com.mvc.userservice.enums.UserRole;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User{
    public Admin(){
        super();
        this.setRole(UserRole.Admin);
    }
}
