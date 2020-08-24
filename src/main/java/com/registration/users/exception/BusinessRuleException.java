package com.registration.users.exception;

public class BusinessRuleException extends RuntimeException{
    
    public BusinessRuleException(String message){
        super(message);
    }
}