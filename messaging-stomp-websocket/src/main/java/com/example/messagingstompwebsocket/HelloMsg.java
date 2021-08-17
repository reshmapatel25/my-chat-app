package com.example.messagingstompwebsocket;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;

@Setter
@Getter
public class HelloMsg {

    String recipientId;
    String senderId;
    String message;
    String name;

    public HelloMsg(){

    }
    public HelloMsg(String name){
        this.name=name;
    }
}
