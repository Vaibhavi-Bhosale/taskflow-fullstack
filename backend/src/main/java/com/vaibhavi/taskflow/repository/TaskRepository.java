package com.vaibhavi.taskflow.repository;

import com.vaibhavi.taskflow.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long>{

}