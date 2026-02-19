package com.codewithrohit.mongodemo.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class AssociatedEntityId {
    private String key;
    private String value;
}

