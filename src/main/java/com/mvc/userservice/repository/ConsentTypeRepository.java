package com.mvc.userservice.repository;

import com.mvc.userservice.entity.ConsentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConsentTypeRepository extends JpaRepository<ConsentType, UUID> {
    List<ConsentType> findAllByActive(boolean active);
    ConsentType findByName(String name);
    Optional<ConsentType> findByCode(String code);
}
