package com.codewithrohit.mongodemo.repository;

import com.codewithrohit.mongodemo.entity.TodoEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface TodoRepository extends MongoRepository<TodoEntity, String> {

    @Query("""
            {
              "_id": ?0,
              "$or": [
                { "createdBy.userId": ?1 },
                { "assignees.userId": ?1 }
              ]
            }
            """)
    Optional<TodoEntity> findAuthorizedTodo(String id, String userId);
}
