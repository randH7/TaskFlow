package com.rand.TaskFlow.repository;

import com.rand.TaskFlow.entity.Employ;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployRepository extends JpaRepository<Employ, String> {
    Employ findByUsername(String employUsername);

}
