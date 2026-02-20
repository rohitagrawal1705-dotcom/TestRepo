package com.codewithrohit.mongodemo.dtos.request;

import com.codewithrohit.mongodemo.model.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateTodoRequest {

    private String title;

    private String description;

    private Priority priority;

    private LocalDateTime dueDate;

    private Set<@Valid UserInfo> assignedToUsers;

    private Set<AssociatedEntities> associatedEntities;

    private ReminderRequest reminder;
}
