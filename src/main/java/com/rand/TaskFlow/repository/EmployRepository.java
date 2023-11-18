package com.rand.TaskFlow.repository;

import com.rand.TaskFlow.DTO.ListOfEmployDTO;
import com.rand.TaskFlow.entity.Employ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployRepository extends JpaRepository<Employ, String> {

    Employ findByUsername(String employUsername);

    @Query("SELECT new com.rand.TaskFlow.DTO.ListOfEmployDTO(e.username, e.email, e.employName, e.jobTitle) FROM Employ e WHERE e.manager.username = ?1")
    List<ListOfEmployDTO> findByManager(String mangerUsername);

    @Query("SELECT DISTINCT new com.rand.TaskFlow.DTO.ListOfEmployDTO(e.username, e.email, e.employName, e.jobTitle) FROM Employ e JOIN ProjectAssignment pa1 ON e.username = pa1.employ.username JOIN ProjectAssignment pa2 ON  pa1.project.projectId = pa2.project.projectId WHERE e.username != ?1 AND pa2.employ.username = ?1 ")
    List<ListOfEmployDTO> findContributorEmployees(String employUsername);

}
