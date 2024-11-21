package com.handson.project.uber.uber.exceptions;

public class RuntimeConflictException extends RuntimeException{
    public RuntimeConflictException(){}
    public RuntimeConflictException(String message){
        super(message);
    }
}
