package com.codewithrohit.mongodemo.service;

import com.codewithrohit.mongodemo.dtos.request.AddCommentRequest;
import com.codewithrohit.mongodemo.dtos.request.CreateTodoRequest;
import com.codewithrohit.mongodemo.dtos.request.UpdateTodoRequest;
import com.codewithrohit.mongodemo.dtos.response.TodoDetailResponse;
import com.codewithrohit.mongodemo.entity.*;
import com.codewithrohit.mongodemo.exception.ResourceNotFoundException;
import com.codewithrohit.mongodemo.mapper.TodoMapper;
import com.codewithrohit.mongodemo.model.UserInfo;
import com.codewithrohit.mongodemo.repository.TodoRepository;
import com.codewithrohit.mongodemo.security.CallerType;
import com.codewithrohit.mongodemo.security.RequestContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;

    public TodoDetailResponse createTodo(CreateTodoRequest request,
                                         RequestContext context) {

        TodoEntity entity = todoMapper.toEntity(request);
        UserInfo creator = buildUserFromContext(context);
        entity.initializeForCreate(
                creator,
                context.getProduct(),
                context.getCallerType(),
                context.getClientId()
        );

        entity.assignUsers(request.getAssignedToUserIds(),
                context.getCallerType() == CallerType.USER
        );

        entity.addInitialComment(
                request.getComment(),
                creator
        );

        if (request.getReminder() != null) {
            entity.configureReminder(
                    request.getReminder().getRemindAt()
            );
        }

        return todoMapper.toDetailResponse(
                todoRepository.save(entity)
        );
    }

    public TodoDetailResponse getTodoById(String id, RequestContext context) {

        TodoEntity entity = todoRepository
                .findAccessibleTodo(id, context.getProduct(), context.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found"));

        return todoMapper.toDetailResponse(entity);
    }


    private UserInfo buildUserFromContext(RequestContext context) {
        return UserInfo.builder()
                .userId(context.getUserId())
                .firstName(context.getFirstName())
                .lastName(context.getLastName())
                .email(context.getEmail())
                .build();
    }

    public TodoDetailResponse update(String id,
                                     UpdateTodoRequest request,
                                     RequestContext context) {

        TodoEntity entity = fetchAccessible(id, context);

        UserInfo user = buildUserFromContext(context);

        boolean isCreator =
                entity.getCreatedBy()
                        .getUserId()
                        .equals(user.getUserId());

        entity.update(request, user, isCreator);

        return todoMapper.toDetailResponse(
                todoRepository.save(entity)
        );
    }

    public TodoDetailResponse addComment(String id,
                                         AddCommentRequest request,
                                         RequestContext context) {

        TodoEntity entity = fetchAccessible(id, context);

        UserInfo user = buildUserFromContext(context);

        entity.addComment(request.getMessage(), user);

        return todoMapper.toDetailResponse(
                todoRepository.save(entity)
        );
    }

    public TodoDetailResponse complete(String id,
                                       RequestContext context) {

        TodoEntity entity = fetchAccessible(id, context);

        UserInfo user = buildUserFromContext(context);

        entity.complete(user);

        return todoMapper.toDetailResponse(
                todoRepository.save(entity)
        );
    }

    public void delete(String id,
                       RequestContext context) {

        TodoEntity entity = fetchAccessible(id, context);

        UserInfo user = buildUserFromContext(context);

        if (context.getCallerType() == CallerType.USER) {

            entity.delete(user);

        } else {

            // SERVICE rule: allow delete only if creator belongs to service
            entity.delete(user);
        }

        todoRepository.save(entity);
    }

    private TodoEntity fetchAccessible(String id,
                                       RequestContext context) {

        if (context.getCallerType() == CallerType.SERVICE) {

            return todoRepository
                    .findByIdAndProductAndClientId(
                            id,
                            context.getProduct(),
                            context.getClientId()
                    )
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Todo not found"));
        }

        return todoRepository
                .findAccessibleTodo(
                        id,
                        context.getProduct(),
                        context.getUserId()
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException("Todo not found"));
    }
}
