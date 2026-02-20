package com.codewithrohit.mongodemo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reminder {
    private String reminderId;
    private LocalDateTime remindAt;
    private boolean notified;
}
