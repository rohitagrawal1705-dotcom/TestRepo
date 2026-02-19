package com.codewithrohit.mongodemo.dtos.response;

import com.codewithrohit.mongodemo.entity.AssociatedEntities;
import com.codewithrohit.mongodemo.entity.Priority;
import com.codewithrohit.mongodemo.entity.State;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TodoResponse {

    private String id;

    private String product;

    private String title;

    private String description;

    private Priority priority;

    private State state;

    private LocalDateTime dueDate;

    private String createdBy;

    private Set<String> assignedToUserIds;

    private boolean selfAssigned;

    private Set<AssociatedEntitiesResponse> associatedEntities;

    private Set<CommentResponse> comments;

    private ReminderResponse reminder;

    private LocalDateTime createdAt;

    private String modifiedBy;

    private LocalDateTime modifiedAt;
}
