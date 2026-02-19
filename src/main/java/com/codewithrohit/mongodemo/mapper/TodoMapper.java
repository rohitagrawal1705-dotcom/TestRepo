package com.codewithrohit.mongodemo.mapper;

import com.codewithrohit.mongodemo.dtos.request.AssociatedEntitiesRequest;
import com.codewithrohit.mongodemo.dtos.request.AssociatedEntityIdRequest;
import com.codewithrohit.mongodemo.dtos.request.CreateTodoRequest;
import com.codewithrohit.mongodemo.dtos.response.*;
import com.codewithrohit.mongodemo.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

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

    TodoResponse toResponse(TodoEntity entity);

    AssociatedEntities toAssociatedEntities(AssociatedEntitiesRequest request);

    AssociatedEntityId toAssociatedEntityId(AssociatedEntityIdRequest request);

    AssociatedEntitiesResponse toAssociatedEntitiesResponse(AssociatedEntities entity);

    AssociatedEntityIdResponse toAssociatedEntityIdResponse(AssociatedEntityId id);

    CommentResponse toCommentResponse(Comments comment);

    ReminderResponse toReminderResponse(Reminder reminder);
}
