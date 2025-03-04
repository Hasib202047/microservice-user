package com.microservice.userService.exception;

public class AlreadyExistException extends RuntimeException{
    public AlreadyExistException(String message)
    {
        super(message);
    }
}
