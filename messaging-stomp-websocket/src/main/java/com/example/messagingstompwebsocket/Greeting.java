package com.example.messagingstompwebsocket;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter

public class Greeting {
    String message;

    public Greeting(String s) {
        message=s;
    }
}
