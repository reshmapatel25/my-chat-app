import { Component, OnInit } from '@angular/core';
import { MessageService } from '../message.service';
import { Client, Message,  Stomp,  StompSubscription } from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';
import { AuthenticationService } from '../service/authentication.service';
import { User } from '../model/user';
 

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {
  title = 'Chat Application';
  description = 'Angular-WebSocket-Spring-Stomp-SockJS';
  private stompClient = null;
  greetings: string[] = [];
  userlist: string[] = [];
  disabled = true;
  name: string;
  user:string;
  recepient:string;
  constructor( private authService:AuthenticationService  ) {
    let currentUser = this.authService.currentUserValue;
      if (currentUser && currentUser.email) {
            this.user=currentUser.email;
      }
   }

  ngOnInit(): void {
  }
  setConnected(connected: boolean) {
    this.disabled = !connected;

    if (connected) {
      this.greetings = [];
      this.userlist=[];
    }
  }

  connect() {
    const socket = new SockJS('http://localhost:8080/gs-guide-websocket');
    this.stompClient = Stomp.over(socket);

    const _this = this;
    this.stompClient.connect({}, function (frame) {
      _this.setConnected(true);
      console.log('Connected: ' + frame);

   /*   _this.stompClient.subscribe('/topic/greetings', function (hello) {
        console.log(" hello " + JSON.parse(hello.body));
        _this.showGreeting(JSON.parse(hello.body).message);
      });
    });*/
    console.log(_this.user);
    
    _this.stompClient.subscribe('/topic/'+ _this.user + "/message", function (hello) {
      let msg=JSON.parse(hello.body);
      console.log(msg);
      _this.showGreeting(msg.senderId + " : " + msg.message);
    });
    _this.stompClient.subscribe('/topic/newuser', function (hello) {
      let usr=JSON.parse(hello.body);
      console.log(usr);
      _this.showUserlist(usr.email);
      //_this.showGreeting(msg.senderId + " : " + msg.message);
    });
  });
  }

  disconnect() {
    if (this.stompClient != null) {
      this.stompClient.disconnect();
    }

    this.setConnected(false);
    console.log('Disconnected!');
  }

  sendName() {

    const message = {
      senderId: this.user,
      recipientId: this.recepient,      
      message: this.name,
      timestamp: new Date(),
    };
    this.stompClient.send(
      '/app/chat-message',
      {},
      JSON.stringify(message)
    );
    const tempMessage= this.user+ ":" + this.name ;
    this.greetings.push(tempMessage);
    this.name="";
  }

  showGreeting(message) {
    this.greetings.push(message);
  }
  showUserlist(newuser){
    this.userlist.push(newuser);
  }

}
