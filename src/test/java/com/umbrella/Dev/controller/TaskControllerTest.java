package com.umbrella.Dev.controller;

import com.umbrella.Dev.dto.request.TaskDtoRequest;
import com.umbrella.Dev.dto.response.TaskDtoResponse;
import com.umbrella.Dev.service.TaskService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskControllerTest {
    @Mock
    private TaskService taskService;
    @InjectMocks
    private TaskController taskController;

    private TaskDtoResponse taskDtoResponse;

    @BeforeEach
    void init() {
        taskDtoResponse = TaskDtoResponse.builder()
                .id(1L)
                .username("user")
                .title("Test Task")
                .build();
    }

    @AfterEach
    void destroy() {
        taskDtoResponse = null;
    }

    @Test
    @DisplayName("GET /tasks returns list of tasks of current user")
    void getUserTasks_ReturnsUserTasks() {
        List<TaskDtoResponse> tasks = List.of(taskDtoResponse);
        when(taskService.getUserTasks()).thenReturn(tasks);

        ResponseEntity<List<TaskDtoResponse>> response = taskController.getUserTasks();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tasks, response.getBody());
        verifyNoMoreInteractions(taskService);
    }

    @Test
    @DisplayName("GET /tasks returns list of all tasks")
    void getAll_ReturnsAllTasks() {
        List<TaskDtoResponse> tasks = List.of(taskDtoResponse);
        when(taskService.getAll()).thenReturn(tasks);

        ResponseEntity<List<TaskDtoResponse>> response = taskController.getAll();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tasks, response.getBody());
        verifyNoMoreInteractions(taskService);
    }

    @Test
    @DisplayName("GET /tasks/{id} returns task by ID")
    void getById_ReturnsTask() {
        Long id = 1L;
        when(taskService.getById(id)).thenReturn(taskDtoResponse);

        ResponseEntity<TaskDtoResponse> response = taskController.getById(id);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(taskDtoResponse, response.getBody());
        verifyNoMoreInteractions(taskService);
    }

    @Test
    @DisplayName("POST /tasks creates a new task")
    void create_ReturnsCreatedTask() {
        TaskDtoRequest request = TaskDtoRequest.builder()
                .title("New Task")
                .build();

        when(taskService.create(request)).thenReturn(taskDtoResponse);

        ResponseEntity<TaskDtoResponse> response = taskController.create(request);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(taskDtoResponse, response.getBody());
        verifyNoMoreInteractions(taskService);
    }

    @Test
    @DisplayName("PUT /tasks/{id} updates existing task")
    void update_ReturnsUpdatedTask() throws IllegalAccessException {
        Long id = 1L;
        TaskDtoRequest request = TaskDtoRequest.builder()
                .title("Updated Task")
                .build();

        when(taskService.update(id, request)).thenReturn(taskDtoResponse);

        ResponseEntity<TaskDtoResponse> response = taskController.update(id, request);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(taskDtoResponse, response.getBody());
        verifyNoMoreInteractions(taskService);
    }

    @Test
    @DisplayName("DELETE /tasks/{id} deletes task")
    void delete_ValidTaskId_ReturnsNoContent() throws IllegalAccessException {
        Long id = 1L;
        doNothing().when(taskService).delete(id);

        ResponseEntity<?> response = taskController.delete(id);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(taskService).delete(id);
        verifyNoMoreInteractions(taskService);
    }
}