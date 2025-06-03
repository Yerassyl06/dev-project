package com.umbrella.Dev.service;

import com.umbrella.Dev.dto.request.TaskDtoRequest;
import com.umbrella.Dev.dto.response.TaskDtoResponse;
import com.umbrella.Dev.entity.Task;

import java.util.List;

public interface TaskService {
    List<TaskDtoResponse> getAll();
    TaskDtoResponse getById(Long id);
    TaskDtoResponse create(TaskDtoRequest request);
    TaskDtoResponse update(Long id, TaskDtoRequest request);
    void delete(Long id);
}
