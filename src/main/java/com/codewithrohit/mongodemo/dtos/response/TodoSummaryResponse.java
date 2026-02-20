package com.codewithrohit.mongodemo.dtos.response;

import com.codewithrohit.mongodemo.model.Priority;
import com.codewithrohit.mongodemo.model.State;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TodoSummaryResponse {

    private String id;

    private String title;

    private Priority priority;

    private State state;

    private LocalDateTime dueDate;

    private UserSummaryResponse createdBy;

    private Set<UserSummaryResponse> assignedToUsers;

    private boolean selfAssigned;
}
