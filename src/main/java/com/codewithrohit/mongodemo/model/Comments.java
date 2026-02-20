package com.codewithrohit.mongodemo.model;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comments {
    private String comment;
    private UserInfo commentedBy;
    private LocalDateTime commentedAt;
}
