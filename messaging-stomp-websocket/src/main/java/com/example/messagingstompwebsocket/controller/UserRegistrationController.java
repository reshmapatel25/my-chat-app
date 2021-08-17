package com.example.messagingstompwebsocket.controller;


import com.example.messagingstompwebsocket.model.User;
import com.example.messagingstompwebsocket.service.UserRegistrationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
public class UserRegistrationController {
    @Autowired
    UserRegistrationService userRegistrationService;

    @GetMapping("/user")
    public String getThisUser(Principal principal){
        //Principal principal=this
        return userRegistrationService.getThisUser(principal);
    }
    @GetMapping("/users")
    public List<User> getAllUsers(){
    // List<User>  userList=
     return userRegistrationService.getAllUsers();
    }
    @PostMapping("/users/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User registerUser(@RequestBody User user ) throws  UnsupportedEncodingException {
    //User user1=
    return userRegistrationService.registerUser(user);
    }
    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        System.out.println(request.getRequestURL());
        System.out.println(siteURL);
        String s= siteURL.replace(request.getServletPath(), "");
        System.out.println(s);
        return s;
    }


}
