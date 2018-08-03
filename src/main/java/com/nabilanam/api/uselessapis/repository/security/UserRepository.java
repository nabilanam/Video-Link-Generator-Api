package com.nabilanam.api.uselessapis.repository.security;

import com.nabilanam.api.uselessapis.model.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByUsername(String username);
}
