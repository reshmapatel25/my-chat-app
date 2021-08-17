import { Injectable } from '@angular/core';
import { User } from '../model/user';
 
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  constructor(private http: HttpClient) { }

  register(user:User):Observable<User>{
    return this.http.post<User>(`http://localhost:8080/users/register`, user);
  }
}
