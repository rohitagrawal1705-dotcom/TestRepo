package com.codewithrohit.mongodemo.dtos.request;

import com.codewithrohit.mongodemo.entity.Priority;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;


@Data
public class CreateTodoRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    private Priority priority;

    private LocalDateTime dueDate;

    private Set<String> assignedToUserIds;

    private Set<AssociatedEntitiesRequest> associatedEntities;

    private String initialComment;

    private ReminderRequest reminder;
}