package com.nabilanam.api.uselessapis.repository;

import com.nabilanam.api.uselessapis.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}