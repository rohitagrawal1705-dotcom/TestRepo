package com.codewithrohit.mongodemo.dtos.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ReminderResponse {

    private LocalDateTime remindAt;
    private boolean notified;
}

