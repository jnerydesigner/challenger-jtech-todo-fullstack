package com.jandernery.jtech.infra.database.jpa;

import com.jandernery.jtech.infra.database.entities.TaskJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskJpaRepository extends JpaRepository<TaskJpaEntity, UUID> {
    Optional<List<TaskJpaEntity>> findByUserId(UUID userId);
}
