package com.umbrella.Dev.dto.response;

import com.umbrella.Dev.entity.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TaskDtoResponseTest {
    @Test
    void testNoArgsConstructor() {
        TaskDtoResponse response = new TaskDtoResponse();
        assertNotNull(response);
    }

    @Test
    void testGettersAndSetters() {
        TaskDtoResponse response = TaskDtoResponse.builder().id(1L).build();
        assertEquals(1L, response.getId());
    }

    @Test
    void testEqualsAndHashCode() {
        // given
        TaskDtoResponse response = TaskDtoResponse.builder().id(1L).build();
        TaskDtoResponse response2 = TaskDtoResponse.builder().id(1L).build();

        // then
        assertEquals(response2, response);
        assertEquals(response2.hashCode(), response.hashCode());
    }

    @Test
    void testToString() {
        TaskDtoResponse response = TaskDtoResponse.builder().id(1L).build();

        String expected = "TaskDtoResponse(id=1, username=null, title=null)";
        assertEquals(expected, response.toString());
    }
}
