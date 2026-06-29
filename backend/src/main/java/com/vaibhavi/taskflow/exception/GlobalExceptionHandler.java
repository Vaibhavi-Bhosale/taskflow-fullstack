package com.vaibhavi.taskflow.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import com.vaibhavi.taskflow.dto.ErrorResponse;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TaskNotFoundException.class)


    public ResponseEntity<ErrorResponse> handleTaskNotFoundException(TaskNotFoundException ex)
    {

          ErrorResponse error = new ErrorResponse( " ooh bhaii Task not found", 404) ;


          return  ResponseEntity
                  .status(HttpStatus.NOT_FOUND)
                  .body( error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex )
    {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage());
    }
}
