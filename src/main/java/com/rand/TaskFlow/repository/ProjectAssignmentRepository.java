package com.rand.TaskFlow.repository;

import com.rand.TaskFlow.entity.Employ;
import com.rand.TaskFlow.entity.Project;
import com.rand.TaskFlow.entity.ProjectAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectAssignmentRepository extends JpaRepository<ProjectAssignment, Integer> {

    Optional<ProjectAssignment> findByEmployAndProject(Employ employ, Project project);

    List<ProjectAssignment> findByProject(Project projectId);

}
