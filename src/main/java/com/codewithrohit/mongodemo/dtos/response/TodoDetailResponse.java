package com.codewithrohit.mongodemo.dtos.response;

import com.codewithrohit.mongodemo.model.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TodoDetailResponse {

    private String id;

    private String product;

    private String title;

    private String description;

    private Priority priority;

    private State state;

    private LocalDateTime dueDate;

    private Set<UserInfo> assignedToUserIds;

    private Set<AssociatedEntities> associatedEntities;

    private Set<Comments> comments;

    private Reminder reminder;

    private UserSummaryResponse createdBy;

    private LocalDateTime createdAt;

    private UserSummaryResponse modifiedBy;

    private LocalDateTime modifiedAt;
}
