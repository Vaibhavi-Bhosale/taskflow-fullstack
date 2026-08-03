package com.vaibhavi.taskflow.service;

import com.vaibhavi.taskflow.entity.User;
import com.vaibhavi.taskflow.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    final private UserRepository userRepository;

    private CustomUserDetailsService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByEmail(username);

        if(user.isEmpty())
        {
            throw new UsernameNotFoundException("User Not Found");
        }

        return user.get();
    }
}
