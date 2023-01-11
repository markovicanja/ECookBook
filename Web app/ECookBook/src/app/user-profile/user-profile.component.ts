import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Recipe } from '../model/recipe.model';
import { User } from '../model/user.model';
import { ServiceService } from '../service.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  constructor(private service: ServiceService, private router: Router) { }

  user: User;
  loggedUser: User;
  userRecipes: Recipe[];
  following: boolean;

  ngOnInit(): void {
    this.following = false;

    if (localStorage.getItem("userProfile") == null || localStorage.getItem("userProfile") == "") {
      this.router.navigate(["home"]);
    }
    else this.user = JSON.parse(localStorage.getItem("userProfile")!);

    if (localStorage.getItem("user") == null || localStorage.getItem("user") == "") {
      this.router.navigate(["home"]);
    }
    else this.loggedUser = JSON.parse(localStorage.getItem("user")!);

    this.userRecipes = [];
    
    this.service.getUserRecipes(this.user.username).subscribe(res => {
      if(res["status"] == 1){
        this.userRecipes = res["poruka"];
      }
    });

    this.service.getIsFollowing(this.loggedUser.username, this.user.username).subscribe(res => {
      if(res["status"] == 1){
        this.following = true;
      }
    });
  }

  follow() {    
    this.service.setIsFollowing(this.loggedUser.username, this.user.username).subscribe(res => {
      if(res["status"] == 1){
        this.following = true;
      }
    });
  }

  unfollow() {    
    this.service.unfollow(this.loggedUser.username, this.user.username).subscribe(res => {
      if(res["status"] == 1){
        this.following = false;
      }
    });
  }

  recipeRoute(recipe: Recipe) {
    localStorage.setItem("recipe", JSON.stringify(recipe));
    this.router.navigate(["recipe"]);
  }
}
