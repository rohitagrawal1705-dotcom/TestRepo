package com.codewithrohit.mongodemo.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class Comments {
    private String comment;
    private String commentedBy;
    private LocalDateTime commentedAt;
}
