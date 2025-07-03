package com.umbrella.Dev.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class TaskDtoResponse {
    private Long id;
    private String username;
    private String title;
}
