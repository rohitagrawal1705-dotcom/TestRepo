package com.codewithrohit.mongodemo.repository;

import com.codewithrohit.mongodemo.entity.TodoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TodoRepository extends MongoRepository<TodoEntity, String> {
}
