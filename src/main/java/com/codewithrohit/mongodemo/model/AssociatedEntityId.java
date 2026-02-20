package com.codewithrohit.mongodemo.model;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssociatedEntityId {

    @NotBlank
    private String key;

    @NotBlank
    private String value;
}

