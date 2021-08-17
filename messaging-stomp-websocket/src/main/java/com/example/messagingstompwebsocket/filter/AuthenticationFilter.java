package com.example.messagingstompwebsocket.filter;



import com.example.messagingstompwebsocket.model.JWTResponse;
import com.example.messagingstompwebsocket.model.LoginRequest;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.fasterxml.jackson.databind.util.JSONWrappedObject;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.messaging.converter.GsonMessageConverter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    private SimpMessagingTemplate messagingTemplate;

    public AuthenticationFilter(AuthenticationManager authenticationManager,SimpMessagingTemplate messagingTemplate)

    {this.messagingTemplate=messagingTemplate;

        this.authenticationManager = authenticationManager;

        setFilterProcessesUrl("/login");

    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException  {

        try {
            LoginRequest creds = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(creds.getUsername(), creds.getPassword(),new ArrayList<>()));
        }catch(IOException e) {
            throw new RuntimeException("Could not read request" + e);
        }
    }

    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, Authentication authentication) throws IOException {
        String token = Jwts.builder()
                .setSubject(((User) authentication.getPrincipal()).getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + 864_000_000))
                .signWith(SignatureAlgorithm.HS512, "SecretKeyToGenJWTs".getBytes())
                .compact();
        response.addHeader("Authorization","Bearer " + token);
        JWTResponse res=new JWTResponse();
        res.setToken( token);
        res.setEmail(((User) authentication.getPrincipal()).getUsername());
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        MediaType jsonMimeType = MediaType.APPLICATION_JSON;
        com.example.messagingstompwebsocket.model.User u=new com.example.messagingstompwebsocket.model.User();
        u.setEmail(((User) authentication.getPrincipal()).getUsername());
        response.setContentType(request.getContentType());
        try {
            ServletServerHttpResponse res1 =new ServletServerHttpResponse(response);
            jsonConverter.write	(res, jsonMimeType,res1 );
            messagingTemplate.convertAndSend("/topic/newuser", u) ;
            res1.close();
        }catch(IOException e) {
            e.printStackTrace();
        }catch(HttpMessageNotWritableException e) {
            e.printStackTrace();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}

