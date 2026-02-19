package com.codewithrohit.mongodemo.dtos.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReminderRequest {

    private LocalDateTime remindAt;
}
