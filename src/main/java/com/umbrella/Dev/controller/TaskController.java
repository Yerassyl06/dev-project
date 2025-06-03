package com.umbrella.Dev.controller;

import com.umbrella.Dev.dto.request.TaskDtoRequest;
import com.umbrella.Dev.dto.response.TaskDtoResponse;
import com.umbrella.Dev.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
@AllArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping()
    public ResponseEntity<List<TaskDtoResponse>> getAll() {
        return ResponseEntity.ok(taskService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDtoResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getById(id));
    }

    @PostMapping()
    public ResponseEntity<TaskDtoResponse> create(@RequestBody TaskDtoRequest request) {
        return ResponseEntity.ok(taskService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDtoResponse> update(@PathVariable Long id, @RequestBody TaskDtoRequest request) {
        return ResponseEntity.ok(taskService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
