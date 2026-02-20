package com.codewithrohit.mongodemo.service;

import com.codewithrohit.mongodemo.dtos.request.CreateTodoRequest;
import com.codewithrohit.mongodemo.dtos.response.TodoDetailResponse;
import com.codewithrohit.mongodemo.entity.*;
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

    private UserInfo buildUserFromContext(RequestContext context) {
        return UserInfo.builder()
                .userId(context.getUserId())
                .firstName(context.getFirstName())
                .lastName(context.getLastName())
                .email(context.getEmail())
                .build();
    }
}
