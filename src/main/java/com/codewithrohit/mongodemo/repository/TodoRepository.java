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
              "product": ?1,
              "$or": [
                {"createdBy.userId": ?2},
                {"assignedToUsers.userId": ?2}
              ]
            }
            """)
    Optional<TodoEntity> findAccessibleTodo(
            String id,
            String product,
            String userId
    );


    Optional<TodoEntity> findByIdAndProductAndClientId(
            String id,
            String product,
            String clientId
    );
}
