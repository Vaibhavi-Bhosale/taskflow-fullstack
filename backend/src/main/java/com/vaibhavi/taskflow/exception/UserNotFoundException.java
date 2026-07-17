package com.vaibhavi.taskflow.exception;

public class UserNotFoundException extends RuntimeException{
     public UserNotFoundException(String message)
     {

         super(message);
     }
}
