package com.codewithrohit.mongodemo.exception;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String onlyCreatorCanModifyTodo) {
        super(onlyCreatorCanModifyTodo);
    }
}
