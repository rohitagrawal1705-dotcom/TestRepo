package com.codewithrohit.mongodemo.dtos.request;

import com.codewithrohit.mongodemo.model.AssociatedEntities;
import com.codewithrohit.mongodemo.model.Priority;
import com.codewithrohit.mongodemo.model.UserInfo;
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

    private Set<@Valid UserInfo> assignedToUserIds;

    private Set<@Valid AssociatedEntities> associatedEntities;

    private String comment;

    private ReminderRequest reminder;
}