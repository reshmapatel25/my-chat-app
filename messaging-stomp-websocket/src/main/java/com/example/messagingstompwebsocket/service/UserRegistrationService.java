package com.example.messagingstompwebsocket.service;

import com.example.messagingstompwebsocket.error.RecordAlreadyExistException;
import com.example.messagingstompwebsocket.model.User;
import com.example.messagingstompwebsocket.repository.UserRepository;

import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class UserRegistrationService {
    @Autowired
    UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public List<User> getAllUsers(){
        return userRepo.findAll();
    }

    public String getThisUser(Principal principal) {
        return principal.getName();
    }

    public User registerUser(User user) throws  UnsupportedEncodingException {
      try{
          if(checkIfUserExist(user.getEmail()) ){
              throw new RecordAlreadyExistException("User already exists for this email or mobile");
          }
         // User uEntity = new User();
         // BeanUtils.copyProperties(user, uEntity);

       //   user.setVerificationCode(generateVerificationCode(user));
          user.setPassword(passwordEncoder.encode(user.getPassword()));
          User u1=userRepo.save(user);
          return u1;
      }catch(DataIntegrityViolationException  dive){
          throw new RecordAlreadyExistException();
      }
        //System.out.println(user);

    }

    private boolean checkIfUserExist(String username) {
        return userRepo.findByEmail(username)!=null ? true : false;
    }


    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }
}
