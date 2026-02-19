package com.codewithrohit.mongodemo.dtos.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentResponse {
    private String comment;
    private String commentedBy;
    private LocalDateTime commentedAt;
}
