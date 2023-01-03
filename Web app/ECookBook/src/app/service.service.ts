import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from './model/user.model';

export interface keyable {
  [key: string]: any  
}

@Injectable({
  providedIn: 'root'
})

export class ServiceService {

  constructor(private http: HttpClient) { }

  uri = 'http://localhost:1234'

  // GET ALL USERS
  getAllUsers() {
    return this.http.get<User[]>(`${this.uri}/getAllUsers`);
  }

  // REGISTER USER
  registerUser(username: string, email: string, password: string) {
    const data = {
      username: username,
      email: email,
      password: password
    }
    return this.http.post<keyable>(`${this.uri}/registerUser`, data);
  }
  
  // LOGIN
  loginUser(username: string, password: string) {
    const data = {
      username: username,
      password: password
    }
    return this.http.post<keyable>(`${this.uri}/loginUser`, data);
  }

  // FIND
  findUser(username: string) {
    const data = {
      username: username
    }
    return this.http.post<keyable>(`${this.uri}/findUser`, data);
  }
}
