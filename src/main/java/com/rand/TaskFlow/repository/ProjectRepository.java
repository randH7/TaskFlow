package com.rand.TaskFlow.repository;

import com.rand.TaskFlow.DOT.ListOfProjectsDOT;
import com.rand.TaskFlow.entity.Manger;
import com.rand.TaskFlow.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    Project findByProjectName(String projectName);

    @Query("SELECT new com.rand.TaskFlow.DOT.ListOfProjectsDOT(p.projectName, p.startDate, p.dueDate, p.manger, p.leader, p.projectStatus) FROM Project p JOIN ProjectAssignment pa ON p.projectId = pa.project.projectId WHERE pa.teamMember.username = ?1")
    List<ListOfProjectsDOT> findByUsernameForTeamMember(String username);

    @Query("SELECT new com.rand.TaskFlow.DOT.ListOfProjectsDOT(p.projectName, p.startDate, p.dueDate, p.manger, p.leader, p.projectStatus) FROM Project p WHERE p.manger.username = ?1")
    List<ListOfProjectsDOT> findByUsernameForManger(String username);

    Optional<Project> findByMangerAndProjectId(Manger manger, Integer project);

    Project findByProjectId(Integer projectId);

    // for test purpose
    Project findTopByOrderByProjectIdDesc();
}
