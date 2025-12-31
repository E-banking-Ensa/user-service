package com.mvc.userservice.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;

@Entity
@DiscriminatorValue("ADMIN")
@AllArgsConstructor
public class Admin extends User{
}
