package com.rand.TaskFlow.repository;

import com.rand.TaskFlow.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String teammember);
}
