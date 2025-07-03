package com.umbrella.Dev.dto.request;

import io.swagger.v3.oas.annotations.info.Info;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Data
@SuperBuilder(toBuilder = true)
public class TaskDtoRequest {
    @NotNull(message = "title can not be null")
    private String title;
}
