package com.example.messagingstompwebsocket.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JWTResponse {

    String token;
    String firstName;
    String lastName;
    String id;
    String email;
}
