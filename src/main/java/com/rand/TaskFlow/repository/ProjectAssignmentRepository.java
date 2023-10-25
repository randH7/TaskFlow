package com.rand.TaskFlow.repository;

import com.rand.TaskFlow.entity.Project;
import com.rand.TaskFlow.entity.ProjectAssignment;
import com.rand.TaskFlow.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectAssignmentRepository extends JpaRepository<ProjectAssignment, Integer> {

    Optional<ProjectAssignment> findByTeamMemberAndProject(TeamMember teamMember, Project project);

}
