package com.registration.users.controller;

import javax.validation.Valid;

import com.registration.users.dto.CredentialsDTO;
import com.registration.users.dto.TokenDTO;
import com.registration.users.exception.InvalidPasswordException;
import com.registration.users.model.UserSystem;
import com.registration.users.security.JwtService;
import com.registration.users.service.impl.UserServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {
    
    private final UserServiceImpl userService; 
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserSystem save( @RequestBody @Valid UserSystem user){
        String passwordEncrypted  = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordEncrypted);
        return userService.save(user);
    }

    @PostMapping("/auth")
    public TokenDTO auth(@RequestBody CredentialsDTO credentials){

        try {
            UserSystem userSystem =  UserSystem.builder()
                .login(credentials.getLogin())
                .password(credentials.getPassword()).build();
            UserDetails authenticatedUser = userService.auth(userSystem);               
            String token = jwtService.generateToken(userSystem);
            return new TokenDTO(userSystem.getLogin(), token);
        } catch (UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (InvalidPasswordException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }        
    }
}