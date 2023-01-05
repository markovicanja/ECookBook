import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Recipe } from '../model/recipe.model';
import { User } from '../model/user.model';
import { ServiceService } from '../service.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  constructor(private service: ServiceService, private router: Router) { }

  user: User;
  userRecipes: Recipe[];
  savedRecipes: Recipe[];
  recommendedRecipes: Recipe[];
  showed: String;
  following: String[];

  ngOnInit(): void {
    if (localStorage.getItem("user") == null) return;
    else this.user = JSON.parse(localStorage.getItem("user")!);

    this.showed = "all";

    this.userRecipes = [];
    this.savedRecipes = [];
    this.recommendedRecipes = [];
    this.service.getUserRecipes(this.user.username).subscribe(res => {
      if(res["status"] == 1){
        this.userRecipes = res["poruka"];
      }
    });

    this.following = [];
    this.service.getFollowings(this.user.username).subscribe(res => {
      if(res["status"] == 1){
        this.following = res["poruka"];
      }
    });

    this.service.getAllSavedRecipes(this.user.username).subscribe(res => {
      if(res["status"] == 1){
        this.savedRecipes = res["poruka"];
      }
    });
    
    this.service.getAllRecommendedRecipes(this.user.username).subscribe(res => {
      if(res["status"] == 1){
        this.recommendedRecipes = res["poruka"];
      }
    });
  }

  change(s: String) {
    this.showed = s;
  }

  recipeRoute(recipe: Recipe) {
    localStorage.setItem("recipe", JSON.stringify(recipe));
    this.router.navigate(["recipe"]);
  }

  userRoute(username: String) {
    this.service.findUser(username.toString()).subscribe(res => {
      if(res["status"] == 1){
        localStorage.setItem("userProfile", JSON.stringify({'username' : res["username"], 'email' : res["email"]}))
        this.router.navigate(["userProfile"]);
      }
    })
  }

}
