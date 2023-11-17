package com.rand.TaskFlow.service.implementations;

import com.rand.TaskFlow.entity.Employ;
import com.rand.TaskFlow.entity.Project;
import com.rand.TaskFlow.entity.ProjectAssignment;
import com.rand.TaskFlow.repository.EmployRepository;
import com.rand.TaskFlow.repository.ProjectAssignmentRepository;
import com.rand.TaskFlow.service.interfaces.ProjectAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectAssignmentServiceImpl implements ProjectAssignmentService {

    @Autowired
    private ProjectAssignmentRepository projectAssignmentRepo;

    @Autowired
    private EmployRepository employRepo;

    @Override
    public void assignProjectToEmployees(List<String> employeesUsername, Project project) {
        for (String employUsername: employeesUsername) {
            Employ employSelected = employRepo.findByUsername(employUsername);
            ProjectAssignment projectAssignment = new ProjectAssignment(employSelected, project);
            projectAssignmentRepo.save(projectAssignment);
        }
    }

}
