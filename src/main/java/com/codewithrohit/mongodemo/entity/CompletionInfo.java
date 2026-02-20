package com.codewithrohit.mongodemo.entity;

import com.codewithrohit.mongodemo.model.UserInfo;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompletionInfo {

    private UserInfo completedBy;
    private Instant completedAt;
}