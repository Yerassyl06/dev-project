package com.umbrella.Dev.service;


import com.umbrella.Dev.dto.request.TaskDtoRequest;
import com.umbrella.Dev.dto.response.TaskDtoResponse;
import com.umbrella.Dev.entity.Task;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService{
    private final List<Task> taskList;

    @Override
    public List<TaskDtoResponse> getAll() {
        return taskList
                .stream()
                .map(e -> TaskDtoResponse.builder()
                        .id(e.getId())
                        .title(e.getTitle())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public TaskDtoResponse getById(Long id) {
        Task task = new Task();
        for (int i = 0; i < taskList.size(); i++) {
            if (taskList.get(i).getId().equals(id)) {
                task = taskList.get(i);
            }
        }
        return TaskDtoResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .build();
    }

    @Override
    public TaskDtoResponse create(TaskDtoRequest request) {
        Task task = new Task();
        task.setId(request.getId());
        task.setTitle(request.getTitle());
        taskList.add(task);
        return TaskDtoResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .build();
    }

    @Override
    public TaskDtoResponse update(Long id, TaskDtoRequest request) {
        Task task = new Task();
        for (int i = 0; i < taskList.size(); i++) {
            if (taskList.get(i).getId().equals(id)) {
                task = taskList.get(i);
            }
        }
        task.setTitle(request.getTitle());

        return TaskDtoResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .build();
    }

    @Override
    public void delete(Long id) {
        int index = -1;
        for (int i = 0; i < taskList.size(); i++) {
            if (taskList.get(i).getId().equals(id)) {
                index = i;
            }
        }
        taskList.remove(index);
    }
}
