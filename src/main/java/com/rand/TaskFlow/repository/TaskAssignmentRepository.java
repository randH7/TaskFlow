package com.rand.TaskFlow.repository;

import com.rand.TaskFlow.entity.ProjectAssignment;
import com.rand.TaskFlow.entity.Task;
import com.rand.TaskFlow.entity.TaskAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskAssignmentRepository extends JpaRepository<TaskAssignment, Integer> {

    TaskAssignment deleteByTask(Task task);

    List<TaskAssignment> findByTask(Task task);

}
