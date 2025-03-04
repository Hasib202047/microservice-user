package com.microservice.userService.globalException;


import com.microservice.userService.exception.AlreadyExistException;
import com.microservice.userService.exception.InvalidException;
import com.microservice.userService.exception.NotFoundException;
import com.microservice.userService.response.Response;
import com.microservice.userService.response.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public Response notFoundException(NotFoundException notFoundException)
    {
        return ResponseBuilder.getSuccessResponse(HttpStatus.NOT_FOUND,notFoundException.getMessage(),null);
    }

    @ExceptionHandler(InvalidException.class)
    public Response InvalidException(InvalidException invalidException)
    {
        return ResponseBuilder.getSuccessResponse(HttpStatus.CONFLICT,invalidException.getMessage(),null);
    }
    @ExceptionHandler(AlreadyExistException.class)
    public Response AlreadyExistException(AlreadyExistException alreadyExistException)
    {
        return ResponseBuilder.getSuccessResponse(HttpStatus.CONFLICT,alreadyExistException.getMessage(),null);
    }

    @ExceptionHandler(Exception.class)
    public Response genericException(Exception e)
    {
        return ResponseBuilder.getSuccessResponse(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),null);
    }
}
