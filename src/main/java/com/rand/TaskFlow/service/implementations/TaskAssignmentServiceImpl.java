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

}
