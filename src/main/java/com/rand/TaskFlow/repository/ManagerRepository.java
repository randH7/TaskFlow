package com.rand.TaskFlow.repository;

import com.rand.TaskFlow.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, String> {

    Manager findByUsername(String username);
}
