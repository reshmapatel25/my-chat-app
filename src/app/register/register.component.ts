import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../model/user';
import { RegisterService } from '../service/register.service';
 
 

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  firstName:string;
  lastName:string;
  email:string;
  password:string;

  
  
  constructor(private registerService:RegisterService,private router: Router) { }

  ngOnInit(): void {
  }

  register(){
     let user=new User();
     user.firstName=this.firstName;
     user.lastName = this.lastName;
     user.email=this.email
     user.password=this.password;
     
     this.registerService.register(user).subscribe(data=>{
       console.log(data);
     },
     data => {
      this.router.navigate(['/login']);
      });

  }

}
