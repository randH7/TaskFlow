package com.rand.TaskFlow.service.implementations;

import com.rand.TaskFlow.entity.Employ;
import com.rand.TaskFlow.entity.Task;
import com.rand.TaskFlow.entity.TaskAssignment;
import com.rand.TaskFlow.repository.EmployRepository;
import com.rand.TaskFlow.repository.TaskAssignmentRepository;
import com.rand.TaskFlow.service.interfaces.TaskAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskAssignmentServiceImpl implements TaskAssignmentService {

    @Autowired
    private TaskAssignmentRepository taskAssignmentRepo;

    @Autowired
    private EmployRepository employRepo;

    @Override
    public void assignTaskToEmployees(List<String> employeesUsername, Task task) {
        for (String employUsername: employeesUsername) {
            Employ employSelected = employRepo.findByUsername(employUsername);
            TaskAssignment taskAssignment = new TaskAssignment(employSelected, task);
            taskAssignmentRepo.save(taskAssignment);
        }
    }

    @Override
    public void editAssignTaskToEmployees(List<TaskAssignment> entitiesToDelete, List<TaskAssignment> newTaskAssignments) {
        taskAssignmentRepo.deleteAll(entitiesToDelete);
        taskAssignmentRepo.saveAll(newTaskAssignments);
    }

    @Override
    public List<TaskAssignment> IdentifyEntitiesDelete(List<String> employeesUsername, Task task) {

        List<TaskAssignment> existingTaskAssignments = taskAssignmentRepo.findByTask(task);

        return existingTaskAssignments.stream()
                .filter(taskAssignment -> !employeesUsername.contains(taskAssignment.getEmploy().getUsername()))
                .collect(Collectors.toList());

    }

    @Override
    public List<TaskAssignment> IdentifyTaskAssignmentEmployees(List<String> employeesUsername, Task task) {

        List<TaskAssignment> existingTaskAssignments = taskAssignmentRepo.findByTask(task);


        List<String> newEmployees = IdentifyEntitiesNew(existingTaskAssignments, employeesUsername);

        return newEmployees.stream()
                .map(username -> {
                    return new TaskAssignment(employRepo.findByUsername(username), task);
                })
                .collect(Collectors.toList());

    }

    private List<String> IdentifyEntitiesNew(List<TaskAssignment> existingTaskAssignments, List<String> employeesUsername) {

        return employeesUsername.stream()
                .filter(username -> existingTaskAssignments.stream()
                        .noneMatch(ta -> ta.getEmploy().getUsername().equals(username)))
                .collect(Collectors.toList());
    }

}
