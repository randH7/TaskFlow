package com.rand.TaskFlow.service.interfaces;

import com.rand.TaskFlow.entity.Task;

import java.util.List;

public interface TaskAssignmentService {
    void assignTaskToEmployees(List<String> employeesUsername, Task task);

}
