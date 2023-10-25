package com.rand.TaskFlow.repository;

import com.rand.TaskFlow.entity.Manger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MangerRepository extends JpaRepository<Manger, String> {

    Manger findByUsername(String username);
}
