package com.rand.TaskFlow.service.interfaces;

import com.rand.TaskFlow.entity.Project;
import com.rand.TaskFlow.entity.ProjectAssignment;

import java.util.List;

public interface ProjectAssignmentService {

    void assignProjectToEmployees(List<String> employeesUsername, Project project);

    void editAssignProjectToEmployees(List<ProjectAssignment> entitiesToDelete, List<ProjectAssignment> newProjectAssignments);

    List <ProjectAssignment> IdentifyEntitiesDelete(List<String> employeesUsername, Project project);

    List<ProjectAssignment> IdentifyProjectAssignmentEmployees(List<String> employeesUsername, Project project);

    List<String> findByProjectOrderByEmploy(Integer projectId);

    List<String> findByProjectOrderByEmployUsernames(Integer projectId);

}
