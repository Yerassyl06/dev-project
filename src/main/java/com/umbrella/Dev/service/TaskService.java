package com.umbrella.Dev.service;

import com.umbrella.Dev.dto.request.TaskDtoRequest;
import com.umbrella.Dev.dto.response.TaskDtoResponse;

import java.util.List;

public interface TaskService {
    List<TaskDtoResponse> getUserTasks();
    List<TaskDtoResponse> getAll();
    TaskDtoResponse getById(Long id);
    TaskDtoResponse create(TaskDtoRequest request);
    TaskDtoResponse update(Long id, TaskDtoRequest request) throws IllegalAccessException;
    void delete(Long id) throws IllegalAccessException;
}
