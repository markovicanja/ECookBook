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

  constructor(private http: HttpClient) {
    this.uri = "";
    let arr : string[] = window.location.href.split(/[:]/);
    this.uri = arr[0] + ":" + arr[1] + ":1234";
  }

  uri : string;

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
  
  // LOGIN USER
  loginUser(username: string, password: string) {
    const data = {
      username: username,
      password: password
    }
    return this.http.post<keyable>(`${this.uri}/loginUser`, data);
  }

  // FIND USER
  findUser(username: string) {
    const data = {
      username: username
    }
    return this.http.post<keyable>(`${this.uri}/findUser`, data);
  }

  // GET ALL RECIPES
  getAllRecipes() {
    return this.http.get<keyable>(`${this.uri}/getAllRecipes`);
  }

  // GET ALL RECIPES FROM GIVEN USER
  getUserRecipes(username: string) {
    const data = {
      username: username
    }
    return this.http.post<keyable>(`${this.uri}/getUserRecipes`, data);
  }

  // ADD NEW RECIPE
  addRecipe(name: string, difficulty: number, category: string, cuisine: string, img: string, description: string, author: string, rating: number) {
    const data = {
      name: name,
      difficulty: difficulty,
      category: category,
      cuisine: cuisine,
      img: img,
      description: description,
      author: author,
      rating: rating
    }
    return this.http.post<keyable>(`${this.uri}/addRecipe`, data);
  }

  // GET ALL PEOPLE THE GIVEN USER IS FOLLOWING
  getFollowings(username: string) {
    const data = {
      username: username
    }
    return this.http.post<keyable>(`${this.uri}/getFollowings`, data);
  }

  // GET ALL COMMENTS FOR A RECIPE
  getComments(recipe: string) {
    const data = {
      recipe: recipe
    }
    return this.http.post<keyable>(`${this.uri}/getComments`, data);
  }

}
