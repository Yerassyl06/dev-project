package com.umbrella.Dev.service;

import com.umbrella.Dev.advice.ForbiddenException;
import com.umbrella.Dev.dto.request.TaskDtoRequest;
import com.umbrella.Dev.dto.response.TaskDtoResponse;
import com.umbrella.Dev.entity.Task;
import com.umbrella.Dev.repository.TaskRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    private Task task;
    private TaskDtoRequest request;
    private final String USERNAME = "testUser";

    @BeforeEach
    void init() {
        task = Task.builder()
                .id(1L)
                .username(USERNAME)
                .title("Sample Task")
                .build();

        request = TaskDtoRequest.builder()
                .title("New Task Title")
                .build();

        SecurityContextHolder.setContext(securityContext);
        lenient().when(securityContext.getAuthentication()).thenReturn(authentication);
        lenient().when(authentication.getName()).thenReturn(USERNAME);
    }


    @AfterEach
    void cleanup() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void testGetUserTasks_ReturnsList() {
        when(taskRepository.findAllByUsername(USERNAME)).thenReturn(List.of(task));

        List<TaskDtoResponse> result = taskService.getUserTasks();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(task.getId(), result.get(0).getId());
        verify(taskRepository).findAllByUsername(USERNAME);
    }

    @Test
    void testGetAll_ReturnsAllTasks() {
        when(taskRepository.findAll()).thenReturn(List.of(task));

        List<TaskDtoResponse> result = taskService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(task.getId(), result.get(0).getId());
        verify(taskRepository).findAll();
    }

    @Test
    void testGetById_ReturnsTaskDto() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        TaskDtoResponse result = taskService.getById(1L);

        assertNotNull(result);
        assertEquals(task.getId(), result.getId());
        assertEquals(task.getUsername(), result.getUsername());
        verify(taskRepository).findById(1L);
    }

    @Test
    void testCreate_SavesTaskAndReturnsDto() {
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        TaskDtoResponse result = taskService.create(request);

        assertNotNull(result);
        assertEquals(task.getId(), result.getId());
        assertEquals(USERNAME, result.getUsername());
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void testUpdate_ValidUser_UpdatesTask() throws IllegalAccessException {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        TaskDtoResponse result = taskService.update(1L, request);

        assertNotNull(result);
        assertEquals(task.getId(), result.getId());
        assertEquals(USERNAME, result.getUsername());
        verify(taskRepository).findById(1L);
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void testUpdate_InvalidUser_ThrowsException() {
        task.setUsername("anotherUser");
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        ForbiddenException ex = assertThrows(ForbiddenException.class,
                () -> taskService.update(1L, request));

        assertEquals("cannot change other user's data", ex.getMessage());
        verify(taskRepository).findById(1L);
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void testDelete_ValidUser_DeletesTask() throws ForbiddenException {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        taskService.delete(1L);

        verify(taskRepository).deleteById(1L);
    }

    @Test
    void testDelete_InvalidUser_ThrowsException() {
        task.setUsername("anotherUser");
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        ForbiddenException ex = assertThrows(ForbiddenException.class,
                () -> taskService.delete(1L));

        assertEquals("cannot delete other user's data", ex.getMessage());
        verify(taskRepository, never()).deleteById(anyLong());
    }
}
