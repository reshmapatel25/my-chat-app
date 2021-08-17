package com.example.messagingstompwebsocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMsg message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }



    @MessageMapping("/chat-message")
    //@SendTo("/topic/user/{recepient}")
    public void message( HelloMsg message) throws Exception {
        messagingTemplate.convertAndSend("/topic/"+message.getRecipientId()+"/message",message);
        //messagingTemplate.convertAndSendToUser(
        //        message.getRecipientId(),"/message",
        //        message);
        //Thread.sleep(1000); // simulated delay
        //return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }


}