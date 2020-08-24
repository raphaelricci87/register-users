package com.registration.users;

import java.util.Arrays;
import java.util.List;

import lombok.Data;
import lombok.Getter;


public class ApiErros {
    
    @Getter
    private List<String> errors;

    public ApiErros(String messageErrors){
        this.errors = Arrays.asList(messageErrors);
    }
}