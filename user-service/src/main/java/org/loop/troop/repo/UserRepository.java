package org.loop.troop.repo;

import java.util.Optional;
import java.util.UUID;

import org.loop.troop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsernameOrId(String username, UUID id);

    boolean existsByUsernameOrId(String username, UUID id);

    void deleteByUsernameOrId(String username, UUID id);
}
