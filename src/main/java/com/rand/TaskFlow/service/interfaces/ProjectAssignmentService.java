package com.rand.TaskFlow.service.interfaces;

import com.rand.TaskFlow.entity.Project;

import java.util.List;

public interface ProjectAssignmentService {

    void assignProjectToEmployees(List<String> employeesUsername, Project project);

}
