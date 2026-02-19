package com.codewithrohit.mongodemo.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Document(collection = "todos")
@CompoundIndexes({
        @CompoundIndex(
                name = "idx_assigned_product_state_due",
                def = "{'assignedToUserIds':1, 'product':1, 'state':1, 'dueDate':1}"
        ),
        @CompoundIndex(
                name = "idx_created_selfassigned_product_state_due",
                def = "{'createdBy':1, 'selfAssigned':1, 'product':1, 'state':1, 'dueDate':1}"
        )
})
@Data
public class TodoEntity {

    @Id
    private String id;

    @Indexed
    private String product;

    private String title;

    private String description;

    private Priority priority = Priority.LOW;

    private State state;

    private LocalDateTime dueDate;

    @Indexed
    private String createdBy;

    @Indexed
    private String clientId;

    @Indexed
    private Set<String> assignedToUserIds = new HashSet<>();

    @Indexed
    private boolean selfAssigned;

    private Set<AssociatedEntities> associatedEntities = new HashSet<>();

    private Set<Comments> comments = new HashSet<>();

    private Reminder reminder;

    private LocalDateTime createdAt;

    private String modifiedBy;

    private LocalDateTime modifiedAt;
}
