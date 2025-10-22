package com.patrick.taskmanager.taskmanager.repository;

import com.patrick.taskmanager.taskmanager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
