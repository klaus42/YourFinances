package ru.klaus42.yourfinances.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.klaus42.yourfinances.entity.User;
import ru.klaus42.yourfinances.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;


@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User myUser = userRepository.findByUsername(username);
        if (myUser == null) {
            throw new UsernameNotFoundException(username);
        }

        try {
            UserDetails user = org.springframework.security.core.userdetails.User.builder()
                    .username(myUser.getName())
                    .password(myUser.getPassword())
                    .roles(myUser.getRole())
                    .build();
            return user;

        } catch (UsernameNotFoundException e){
            System.out.println(e.getMessage());
        }
        return null;

    }

}