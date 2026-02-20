package com.codewithrohit.mongodemo.entity;


import com.codewithrohit.mongodemo.model.*;
import com.codewithrohit.mongodemo.security.CallerType;
import lombok.*;
import com.codewithrohit.mongodemo.exception.BadRequestException;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Document(collection = "todos")
@CompoundIndexes({
        @CompoundIndex(
                name = "idx_assigned_product_state_due",
                def = "{'assignedToUsers.userId':1, 'product':1, 'state':1, 'dueDate':1}"
        ),
        @CompoundIndex(
                name = "idx_created_selfassigned_product_state_due",
                def = "{'createdBy.userId':1, 'selfAssigned':1, 'product':1, 'state':1, 'dueDate':1}"
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
    private UserInfo createdBy;

    @Indexed
    private String clientId;

    @Indexed
    private Set<UserInfo> assignees = new HashSet<>();

    @Indexed
    private boolean selfAssigned;

    private Set<AssociatedEntities> associatedEntities = new HashSet<>();

    private Set<Comments> comments = new HashSet<>();

    private CompletionInfo completionInfo;

    private Reminder reminder;

    private LocalDateTime createdAt;

    private UserInfo modifiedBy;

    private LocalDateTime modifiedAt;

    public void initializeForCreate(UserInfo creator,
                                    String product,
                                    CallerType callerType,
                                    String clientId) {

        this.createdBy = creator;
        this.product = product;
        this.state = State.PENDING;
        this.createdAt = LocalDateTime.now();

        if (callerType == CallerType.SERVICE) {
            this.clientId = clientId;
        }
    }

    public void assignUsers(Set<UserInfo> users, boolean enforceSelf) {

        if (users == null || users.isEmpty()) {
            users = Set.of(this.createdBy);
        }

        if (enforceSelf &&
                users.stream().noneMatch(u ->
                        u.getUserId()
                                .equals(this.createdBy.getUserId()))) {

            throw new BadRequestException(
                    "You must include yourself in assignment"
            );
        }

        this.assignees = new HashSet<>(users);

        recomputeSelfAssigned();
    }

    public void addInitialComment(String comment, UserInfo author) {

        if (!StringUtils.hasText(comment)) {
            return;
        }

        this.comments.add(
                Comments.builder()
                        .comment(comment)
                        .commentedBy(author)
                        .commentedAt(LocalDateTime.now())
                        .build()
        );
    }

    public void configureReminder(LocalDateTime remindAt) {

        if (remindAt == null) {
            return;
        }

        this.reminder = Reminder.builder()
                .remindAt(remindAt)
                .notified(false)
                .build();
    }

    public void markModified(UserInfo modifier) {
        this.modifiedBy = modifier;
        this.modifiedAt = LocalDateTime.now();
    }

    private void recomputeSelfAssigned() {

        this.selfAssigned =
                this.assignees.stream()
                        .anyMatch(u ->
                                u.getUserId()
                                        .equals(this.createdBy.getUserId()));
    }
}
