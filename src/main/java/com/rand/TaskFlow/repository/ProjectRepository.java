package com.rand.TaskFlow.repository;

import com.rand.TaskFlow.DTO.ListOfProjectsDTO;
import com.rand.TaskFlow.entity.Manager;
import com.rand.TaskFlow.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    Project findByProjectName(String projectName);

    @Query("SELECT new com.rand.TaskFlow.DTO.ListOfProjectsDTO(p.projectName, p.startDate, p.dueDate, p.manager, p.leader, p.projectStatus) FROM Project p JOIN ProjectAssignment pa ON p.projectId = pa.project.projectId WHERE pa.employ.username = ?1")
    List<ListOfProjectsDTO> findByUsernameForEmploy(String username);

    @Query("SELECT new com.rand.TaskFlow.DTO.ListOfProjectsDTO(p.projectName, p.startDate, p.dueDate, p.manager, p.leader, p.projectStatus) FROM Project p WHERE p.manager.username = ?1")
    List<ListOfProjectsDTO> findByUsernameForManager(String username);

    Optional<Project> findByManagerAndProjectId(Manager manager, Integer project);

    Project findByProjectId(Integer projectId);

    // for test purpose
    Project findTopByOrderByProjectIdDesc();
}
