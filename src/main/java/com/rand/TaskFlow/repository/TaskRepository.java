package com.rand.TaskFlow.repository;

import com.rand.TaskFlow.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {
}
