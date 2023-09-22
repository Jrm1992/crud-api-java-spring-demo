package com.example.crudapi.controller;

import com.example.crudapi.repository.TaskRepository;
import com.example.crudapi.model.TaskModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskRepository repository;

    @GetMapping
    public List<TaskModel> getTasks() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public TaskModel getTask(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
    }

    @PostMapping
    public TaskModel createTask(@RequestBody TaskModel task) {
        return repository.save(task);
    }

    @PutMapping("/{id}")
    public Optional<TaskModel> updateTask(@PathVariable Long id, @RequestBody TaskModel task) {
        return Optional.ofNullable(repository.findById(id).map(t -> {
            t.setName(task.getName());
            t.setDescription(task.getDescription());
            return repository.save(t);
        }).orElseThrow(() -> new RuntimeException("Task not found")));
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
