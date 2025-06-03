package com.umbrella.Dev.dto.response;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
public class TaskDtoResponse {
    private Long id;
    private String title;
}
