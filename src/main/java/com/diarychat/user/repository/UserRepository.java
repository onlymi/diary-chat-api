package com.diarychat.user.repository;

import com.diarychat.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUserId(String userId);

    boolean existsByEmail(String email);
    
    Optional<User> findByUserId(String userId);
    
    Optional<User> findByEmail(String email);
}
