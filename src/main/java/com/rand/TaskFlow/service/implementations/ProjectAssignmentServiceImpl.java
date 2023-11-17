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
import java.util.stream.Collectors;

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

    @Override
    public void editAssignProjectToEmployees(List<ProjectAssignment> entitiesToDelete, List<ProjectAssignment> newProjectAssignments) {
        projectAssignmentRepo.deleteAll(entitiesToDelete);
        projectAssignmentRepo.saveAll(newProjectAssignments);
    }

    @Override
    public List<ProjectAssignment> IdentifyEntitiesDelete(List<String> employeesUsername, Project project) {

        List<ProjectAssignment> existingProjectAssignments = projectAssignmentRepo.findByProject(project);

        return existingProjectAssignments.stream()
                .filter(projectAssignment -> !employeesUsername.contains(projectAssignment.getEmploy().getUsername()))
                .collect(Collectors.toList());
    }

    // Identify new employees to assign them in the project (new ProjectAssignment entities)
    @Override
    public List<ProjectAssignment> IdentifyProjectAssignmentEmployees(List<String> employeesUsername, Project project) {

        List<ProjectAssignment> existingProjectAssignments = projectAssignmentRepo.findByProject(project);


        List<String> newEmployees = IdentifyEntitiesNew(existingProjectAssignments, employeesUsername);

        return newEmployees.stream()
                .map(username -> {
                    return new ProjectAssignment(employRepo.findByUsername(username), project);
                })
                .collect(Collectors.toList());
    }

    // Filter employeesUsername based on existingProjectAssignments
    private List<String> IdentifyEntitiesNew(List<ProjectAssignment> existingProjectAssignments, List<String> employeesUsername) {

        return employeesUsername.stream()
                .filter(username -> existingProjectAssignments.stream()
                        .noneMatch(pa -> pa.getEmploy().getUsername().equals(username)))
                .collect(Collectors.toList());
    }

}
