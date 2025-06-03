package com.umbrella.Dev.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TaskDtoRequest {
    private Long id;
    private String title;
}
