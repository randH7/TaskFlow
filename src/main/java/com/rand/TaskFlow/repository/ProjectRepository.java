package com.rand.TaskFlow.repository;

import com.rand.TaskFlow.DTO.DetailsProject.DetailsProjectDTO;
import com.rand.TaskFlow.DTO.ListOfProjectsDTO;
import com.rand.TaskFlow.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    Project findByProjectName(String projectName);

    @Query("SELECT new com.rand.TaskFlow.DTO.ListOfProjectsDTO(p.projectName, p.dueDate, p.manager.employName, p.leader.employName, p.projectStatus) FROM Project p JOIN ProjectAssignment pa ON p.projectId = pa.project.projectId WHERE pa.employ.username = ?1")
    List<ListOfProjectsDTO> findProjectsByUsernameForEmploy(String username);

    @Query("SELECT new com.rand.TaskFlow.DTO.ListOfProjectsDTO(p.projectName, p.dueDate, p.manager.employName, p.leader.employName, p.projectStatus) FROM Project p WHERE p.manager.username = ?1")
    List<ListOfProjectsDTO> findProjectsByUsernameForManager(String username);

    @Query("SELECT new com.rand.TaskFlow.DTO.DetailsProject.DetailsProjectDTO(p.projectName, p.startDate, p.dueDate, p.manager.employName, p.leader.employName, p.projectStatus, p.description) FROM Project p JOIN ProjectAssignment pa ON p.projectId = pa.project.projectId WHERE p.projectId = ?1 AND pa.employ.username = ?2")
    DetailsProjectDTO findDetailsByIdForEmploy(Integer projectId, String username);

    @Query("SELECT new com.rand.TaskFlow.DTO.DetailsProject.DetailsProjectDTO(p.projectName, p.startDate, p.dueDate, p.manager.employName, p.leader.employName, p.projectStatus, p.description) FROM Project p WHERE p.projectId = :projectId AND p.manager.username = :username")
    DetailsProjectDTO findDetailsByIdForManager(@Param("projectId") Integer projectId, @Param("username")String username);

    Project findByProjectId(Integer projectId);

    // for test purpose
    Project findTopByOrderByProjectIdDesc();
}
