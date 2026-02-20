package com.codewithrohit.mongodemo.entity;


import com.codewithrohit.mongodemo.dtos.request.UpdateTodoRequest;
import com.codewithrohit.mongodemo.exception.ForbiddenException;
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


    public void update(UpdateTodoRequest request,
                       UserInfo modifier,
                       boolean isCreator) {

        if (!isCreator) {
            throw new ForbiddenException("Only creator can modify todo");
        }

        if (request.getTitle() != null)
            this.title = request.getTitle();

        if (request.getDescription() != null)
            this.description = request.getDescription();

        if (request.getPriority() != null)
            this.priority = request.getPriority();

        if (request.getDueDate() != null)
            this.dueDate = request.getDueDate();

        if (request.getAssociatedEntities() != null)
            this.associatedEntities = request.getAssociatedEntities();

        if (request.getAssignedToUsers() != null) {
            assignUsers(request.getAssignedToUsers(), true);
        }

        if (request.getReminder() != null) {
            configureReminder(request.getReminder().getRemindAt());
        }

        markModified(modifier);
    }

    public void addComment(String comment, UserInfo author) {

        if (!this.createdBy.getUserId().equals(author.getUserId()) &&
                this.assignees.stream()
                        .noneMatch(u -> u.getUserId()
                                .equals(author.getUserId()))) {

            throw new ForbiddenException("Only creator or assignee can comment");
        }

        this.comments.add(
                Comments.builder()
                        .comment(comment)
                        .commentedBy(author)
                        .commentedAt(LocalDateTime.now())
                        .build()
        );

        markModified(author);
    }

    public void complete(UserInfo user) {

        boolean isAssigned =
                this.assignees.stream()
                        .anyMatch(u -> u.getUserId()
                                .equals(user.getUserId()));

        if (!isAssigned) {
            throw new ForbiddenException(
                    "Only assigned user can complete todo");
        }

        this.state = State.COMPLETED;

        markModified(user);
    }

    public void delete(UserInfo user) {

        if (this.state == State.CANCELLED) {
            throw new BadRequestException("Todo already deleted");
        }

        if (!this.createdBy.getUserId()
                .equals(user.getUserId())) {

            throw new ForbiddenException(
                    "Only creator can delete todo");
        }

        this.state = State.CANCELLED;

        markModified(user);
    }
}
