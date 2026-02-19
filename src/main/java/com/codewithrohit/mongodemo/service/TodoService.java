package com.codewithrohit.mongodemo.service;

import com.codewithrohit.mongodemo.dtos.request.CreateTodoRequest;
import com.codewithrohit.mongodemo.dtos.response.TodoResponse;
import com.codewithrohit.mongodemo.entity.Comments;
import com.codewithrohit.mongodemo.entity.Reminder;
import com.codewithrohit.mongodemo.entity.State;
import com.codewithrohit.mongodemo.entity.TodoEntity;
import com.codewithrohit.mongodemo.mapper.TodoMapper;
import com.codewithrohit.mongodemo.repository.TodoRepository;
import com.codewithrohit.mongodemo.security.CallerType;
import com.codewithrohit.mongodemo.security.RequestContext;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;

    public TodoResponse createTodo(CreateTodoRequest request,
                                   RequestContext context) throws BadRequestException {

        TodoEntity entity = todoMapper.toEntity(request);

        entity.setCreatedBy(context.getUserId());
        entity.setCreatedAt(LocalDateTime.now());
        entity.setModifiedBy(context.getUserId());
        entity.setModifiedAt(LocalDateTime.now());

        entity.setProduct(context.getProduct());
        entity.setState(State.PENDING);

        Set<String> assignments =
                Optional.ofNullable(request.getAssignedToUserIds())
                        .orElse(Set.of(context.getUserId()));

        if (context.getCallerType() == CallerType.USER &&
                !assignments.contains(context.getUserId())) {
            throw new BadRequestException(
                    "You must include yourself in assignment"
            );
        }

        entity.setAssignedToUserIds(assignments);

        entity.setSelfAssigned(
                assignments.contains(entity.getCreatedBy())
        );

        if (StringUtils.hasText(request.getInitialComment())) {
            entity.getComments().add(
                    Comments.builder()
                            .comment(request.getInitialComment())
                            .commentedBy(context.getUserId())
                            .commentedAt(LocalDateTime.now())
                            .build()
            );
        }

        if (request.getReminder() != null) {
            entity.setReminder(
                    Reminder.builder()
                            .remindAt(request.getReminder().getRemindAt())
                            .notified(false)
                            .build()
            );
        }

        if (context.getCallerType() == CallerType.SERVICE) {
            entity.setClientId(context.getClientId());
        }

        return todoMapper.toResponse(
                todoRepository.save(entity)
        );
    }
}
