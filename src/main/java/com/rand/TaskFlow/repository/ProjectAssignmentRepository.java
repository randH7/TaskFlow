package com.rand.TaskFlow.repository;

import com.rand.TaskFlow.entity.Employ;
import com.rand.TaskFlow.entity.Project;
import com.rand.TaskFlow.entity.ProjectAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProjectAssignmentRepository extends JpaRepository<ProjectAssignment, Integer> {

    Optional<ProjectAssignment> findByEmployAndProject(Employ employ, Project project);

    List<ProjectAssignment> findByProject(Project project);

    @Query("SELECT pa.employ.employName FROM ProjectAssignment pa WHERE pa.project.projectId = ?1")
    List<String> findByProjectOrderByEmploy(Integer projectId);

    @Query("SELECT pa.employ.username FROM ProjectAssignment pa WHERE pa.project.projectId = ?1")
    List<String> findByProjectOrderByEmployUsernames(Integer projectId);
}
