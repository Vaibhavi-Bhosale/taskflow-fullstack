package com.vaibhavi.taskflow.exception;

import com.vaibhavi.taskflow.dto.ErrorResponse;
import com.vaibhavi.taskflow.entity.Task;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> emailAlreayExitsException( EmailAlreadyExistsException ex)
    {
        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                409
        );

        return  ResponseEntity
                .status(409)
                .body(error);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public  ResponseEntity<ErrorResponse> taskNotFoundException(TaskNotFoundException ex)
    {
        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                404
        );

                return ResponseEntity
                        .status(404)
                        .body(error);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> userNotFoundException(UserNotFoundException ex)
    {
        ErrorResponse error = new ErrorResponse(
                        ex.getMessage(),
                        404
                );

        return  ResponseEntity
                .status(404)
                .body(error);
    }
}