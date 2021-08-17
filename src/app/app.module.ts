import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DashboardComponent } from './dashboard/dashboard.component';

import { FormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { ChatComponent } from './chat/chat.component';
import {BasicAuthHtppInterceptorService} from './service/auth-interceptor';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component'; 
@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    ChatComponent,
    LoginComponent,
    RegisterComponent
  ],
  imports: [
    AppRoutingModule,
    BrowserModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule
    
  ],
  providers: [
    {provide:HTTP_INTERCEPTORS,useClass:BasicAuthHtppInterceptorService,multi:true},
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
