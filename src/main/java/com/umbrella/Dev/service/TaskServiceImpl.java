package com.umbrella.Dev.service;


import com.umbrella.Dev.advice.ApplicationExceptionHandler;
import com.umbrella.Dev.advice.ForbiddenException;
import com.umbrella.Dev.dto.request.TaskDtoRequest;
import com.umbrella.Dev.dto.response.TaskDtoResponse;
import com.umbrella.Dev.entity.Task;
import com.umbrella.Dev.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService{
    private final TaskRepository taskRepository;

    public List<TaskDtoResponse> getUserTasks() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return taskRepository.findAllByUsername(authentication.getName())
                .stream()
                .map(e-> TaskDtoResponse.builder()
                        .id(e.getId())
                        .username(e.getUsername())
                        .title(e.getTitle())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDtoResponse> getAll() {
        return taskRepository.findAll()
                .stream()
                .map(e-> TaskDtoResponse.builder()
                        .id(e.getId())
                        .username(e.getUsername())
                        .title(e.getTitle())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public TaskDtoResponse getById(Long id) {
        Task task = taskRepository.findById(id).get();
        return TaskDtoResponse.builder()
                .id(task.getId())
                .username(task.getUsername())
                .title(task.getTitle())
                .build();
    }

    @Override
    public TaskDtoResponse create(TaskDtoRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Task task = taskRepository.save(Task.builder()
                .username(authentication.getName())
                .title(request.getTitle())
                .build());
        return TaskDtoResponse.builder()
                .id(task.getId())
                .username(task.getUsername())
                .title(task.getTitle())
                .build();
    }

    @Override
    public TaskDtoResponse update (Long id, TaskDtoRequest request) throws ForbiddenException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Task task = taskRepository.findById(id).get();
        if (task.getUsername().equals(authentication.getName())) {
            task.setTitle(request.getTitle());
            taskRepository.save(task);
        }
        else {
            throw new ForbiddenException("cannot change other user's data");
        }
         return TaskDtoResponse.builder()
                 .id(task.getId())
                 .username(task.getUsername())
                 .title(task.getTitle())
                 .build();
    }

    @Override
    public void delete(Long id) throws ForbiddenException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Task task = taskRepository.findById(id).get();
        if (task.getUsername().equals(authentication.getName())) {
            taskRepository.deleteById(id);
        }
        else {
            throw new ForbiddenException("cannot delete other user's data");
        }
    }
}
