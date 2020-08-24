package com.registration.users.service.impl;

import com.registration.users.exception.InvalidPasswordException;
import com.registration.users.model.UserSystem;
import com.registration.users.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserDetailsService{

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Transactional
    public UserSystem save(UserSystem user){
        return repository.save(user);
    }

    public UserDetails auth(UserSystem userSystem){
        UserDetails user = loadUserByUsername(userSystem.getLogin());
        boolean validPassword = encoder.matches(userSystem.getPassword(), user.getPassword());
        if(validPassword){
            return user;
        }
        
        throw new InvalidPasswordException();
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        UserSystem user = repository.findByLogin(username)
            .orElseThrow( () -> new UsernameNotFoundException("User not found"));

        String[] roles = user.isAdmin() ? new String[]{"ADMIN", "USER"} : new String[]{"USER"};

        return User
                .builder()
                .username(user.getLogin())
                .password(user.getPassword())
                .roles(roles)
                .build();
    }

}