package com.rand.TaskFlow.repository;

import com.rand.TaskFlow.entity.Task;
import com.rand.TaskFlow.entity.TaskAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskAssignmentRepository extends JpaRepository<TaskAssignment, Integer> {

    List<TaskAssignment> findByTask(Task task);

    @Query("SELECT ta.employ.employName FROM TaskAssignment ta WHERE ta.task.taskId = ?1")
    List<String> findTaskOrderByByEmploy(Integer taskId);

}
