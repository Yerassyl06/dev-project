package com.umbrella.Dev.controller;

import com.umbrella.Dev.dto.request.TaskDtoRequest;
import com.umbrella.Dev.dto.response.TaskDtoResponse;
import com.umbrella.Dev.entity.Task;
import com.umbrella.Dev.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@AllArgsConstructor
@Tag(
        name = "Task Controller All CRUD API",
        description = "this is the class that implements all the CRUD api related to task management"
)
public class TaskController {
    private final TaskService taskService;

    @Operation(
            summary = "finding all the tasks from database",
            description = "getting all the tasks from db using this api"
    )

    @GetMapping("/my")
    public ResponseEntity<List<TaskDtoResponse>> getUserTasks() {
        return ResponseEntity.ok(taskService.getUserTasks());
    }

    @GetMapping()
    public ResponseEntity<List<TaskDtoResponse>> getAll() {
        return ResponseEntity.ok(taskService.getAll());
    }

    @Operation(
            summary = "finding the task from database by id",
            description = "getting the task from database using this api"
    )
    @GetMapping("/{id}")
    public ResponseEntity<TaskDtoResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getById(id));
    }

    @Operation(
            summary = "creating the task in the database",
            description = "creating the task in the database using this api"
    )
    @PostMapping()
    public ResponseEntity<TaskDtoResponse> create(@RequestBody @Valid TaskDtoRequest request) {
        return ResponseEntity.ok(taskService.create(request));
    }

    @Operation(
            summary = "updating the task by its id",
            description = "updating the task by its id using this api"
    )
    @PutMapping("/{id}")
    public ResponseEntity<TaskDtoResponse> update(@PathVariable Long id, @RequestBody @Valid TaskDtoRequest request) throws IllegalAccessException {
        return ResponseEntity.ok(taskService.update(id, request));
    }

    @Operation(
            summary = "deleting the task by id",
            description = "deleting the task by id using this api"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws IllegalAccessException{
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
