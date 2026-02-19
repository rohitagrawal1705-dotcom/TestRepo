package com.codewithrohit.mongodemo.dtos.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AssociatedEntityIdResponse {
    private String key;
    private String value;
}
