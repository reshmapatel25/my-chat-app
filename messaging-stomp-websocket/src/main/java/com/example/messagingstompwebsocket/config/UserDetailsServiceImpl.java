package com.example.messagingstompwebsocket.config;


import com.example.messagingstompwebsocket.model.User;
import com.example.messagingstompwebsocket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepo;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {   String uname;
        User user = userRepo.findByEmail(username);
        if(user == null) {
            throw new UsernameNotFoundException(username);
        }else{
            if(username.equalsIgnoreCase(user.getEmail())){
                uname=user.getEmail();
            }

        }
        List<GrantedAuthority> authorityList=new ArrayList<>();
        SimpleGrantedAuthority authority=null;
        if(username.equalsIgnoreCase("admin ")){
            authority=new SimpleGrantedAuthority("ADMIN");
        }else{
            authority=new SimpleGrantedAuthority("USER");
        }
        authorityList.add(authority);
       return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorityList);
    }
}
