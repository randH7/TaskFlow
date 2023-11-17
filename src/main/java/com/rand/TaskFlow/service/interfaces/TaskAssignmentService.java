package com.rand.TaskFlow.service.interfaces;

import com.rand.TaskFlow.entity.Task;
import com.rand.TaskFlow.entity.TaskAssignment;

import java.util.List;

public interface TaskAssignmentService {
    void assignTaskToEmployees(List<String> employeesUsername, Task task);

    void editAssignTaskToEmployees(List<TaskAssignment> entitiesToDelete, List<TaskAssignment> newTaskAssignments);

    List<TaskAssignment> IdentifyEntitiesDelete(List<String> employeesUsername, Task task);

    List<TaskAssignment> IdentifyTaskAssignmentEmployees(List<String> employeesUsername, Task task);

}
