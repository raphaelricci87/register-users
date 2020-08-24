package com.registration.users.controller;

import com.registration.users.ApiErros;
import com.registration.users.exception.BusinessRuleException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class AppControllerAdvice {
     
    @ExceptionHandler
    public ApiErros handleBusinessRuleException(BusinessRuleException ex){
        String messageErrors = ex.getMessage();
        return new ApiErros(messageErrors);
    }

}