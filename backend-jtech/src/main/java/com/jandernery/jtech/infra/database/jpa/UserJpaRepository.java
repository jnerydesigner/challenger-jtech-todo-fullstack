package com.jandernery.jtech.infra.database.jpa;

import com.jandernery.jtech.domain.entities.UserEntity;
import com.jandernery.jtech.infra.database.entities.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserJpaEntity, UUID> {
    Optional<UserJpaEntity> findByEmail(String email);
    boolean existsByEmail(String email);



    @Query("""
        select u from UserJpaEntity u
        left join fetch u.tasks t
        where u.id =:id
        order by t.title asc
    """)
    Optional<UserJpaEntity> findByIdWithTasks(UUID id);

}
