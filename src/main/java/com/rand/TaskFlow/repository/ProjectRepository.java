package com.rand.TaskFlow.repository;

import com.rand.TaskFlow.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
}
