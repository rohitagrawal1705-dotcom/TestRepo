package com.codewithrohit.mongodemo.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AssociatedEntityIdRequest {

    @NotBlank
    private String key;

    @NotBlank
    private String value;
}
