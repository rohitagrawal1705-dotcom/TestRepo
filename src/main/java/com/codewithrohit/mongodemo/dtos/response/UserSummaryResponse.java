package com.codewithrohit.mongodemo.dtos.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSummaryResponse {
    private String userId;
}
