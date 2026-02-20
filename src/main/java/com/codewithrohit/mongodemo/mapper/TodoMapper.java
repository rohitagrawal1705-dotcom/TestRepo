package com.codewithrohit.mongodemo.mapper;

import com.codewithrohit.mongodemo.dtos.request.CreateTodoRequest;
import com.codewithrohit.mongodemo.dtos.response.*;
import com.codewithrohit.mongodemo.entity.*;
import com.codewithrohit.mongodemo.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface TodoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "clientId", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "selfAssigned", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "reminder", ignore = true)
    TodoEntity toEntity(CreateTodoRequest request);

    /* ========= Response Mapping ========= */

    TodoSummaryResponse toSummaryResponse(TodoEntity entity);

    TodoDetailResponse toDetailResponse(TodoEntity entity);

    /* ========= User Mapping ========= */

    default UserSummaryResponse map(UserInfo user) {
        if (user == null) return null;
        return UserSummaryResponse.builder()
                .userId(user.getUserId())
                .build();
    }

    default Set<UserSummaryResponse> mapUsers(Set<UserInfo> users) {
        if (users == null) return Set.of();
        return users.stream()
                .map(this::map)
                .collect(Collectors.toSet());
    }
}
