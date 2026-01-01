package com.mvc.userservice.repository;

import com.mvc.userservice.entity.KycDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface KycDocumentRepository extends JpaRepository<KycDocument,UUID> {
    List<KycDocument> findAllByClientId(UUID userId);
}
