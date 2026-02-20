package com.codewithrohit.mongodemo.controller;

import com.codewithrohit.mongodemo.dtos.request.CreateTodoRequest;
import com.codewithrohit.mongodemo.dtos.response.TodoDetailResponse;
import com.codewithrohit.mongodemo.security.RequestContext;
import com.codewithrohit.mongodemo.security.RequestContextResolver;
import com.codewithrohit.mongodemo.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;
    private final RequestContextResolver contextResolver;

    @PostMapping
    public ResponseEntity<TodoDetailResponse> createTodo(
            @Valid @RequestBody CreateTodoRequest request,
            @RequestHeader(value = "X-USER-ID", required = false) String userId,
            @RequestHeader(value = "X-APIM-CLIENT-ID", required = false) String clientId,
            @RequestHeader(value = "X-REQUEST-USER-ID", required = false) String requestUserId,
            @RequestHeader("X-PRODUCT-NAME") String product
    ) {

        RequestContext context =
                contextResolver.resolve(userId, clientId, requestUserId, product);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(todoService.createTodo(request, context));
    }
}
