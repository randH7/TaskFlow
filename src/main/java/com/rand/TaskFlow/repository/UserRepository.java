package com.rand.TaskFlow.repository;

import com.rand.TaskFlow.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
