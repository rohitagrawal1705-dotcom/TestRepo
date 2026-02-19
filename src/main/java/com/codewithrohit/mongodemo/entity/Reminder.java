package com.codewithrohit.mongodemo.entity;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class Reminder {

    private LocalDateTime remindAt;

    private boolean notified;
}
