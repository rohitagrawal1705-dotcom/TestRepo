package com.codewithrohit.mongodemo.model;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssociatedEntities {

    @NotBlank
    private String type;

    @NotEmpty
    private Set<@Valid AssociatedEntityId> ids;
}
