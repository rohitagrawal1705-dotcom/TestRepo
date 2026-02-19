package com.codewithrohit.mongodemo.dtos.response;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class AssociatedEntitiesResponse {

    private String type;

    private Set<AssociatedEntityIdResponse> ids;
}
