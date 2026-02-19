package com.codewithrohit.mongodemo.dtos.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Set;

@Data
public class AssociatedEntitiesRequest {

    @NotBlank
    private String type;

    @NotEmpty
    private Set<@Valid AssociatedEntityIdRequest> ids;
}
