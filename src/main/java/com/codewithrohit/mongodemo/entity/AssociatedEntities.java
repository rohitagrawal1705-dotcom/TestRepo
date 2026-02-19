package com.codewithrohit.mongodemo.entity;


import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class AssociatedEntities {

    private String type;
    private Set<AssociatedEntityId> ids;
}
