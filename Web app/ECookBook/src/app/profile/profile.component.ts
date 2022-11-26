import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { followings } from '../data/following';
import { allRecipes } from '../data/recipes';
import { allUsers } from '../data/users';
import { Following } from '../model/following.model';
import { Recipe } from '../model/recipe.model';
import { User } from '../model/user.model';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  constructor(private router: Router) { }

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

    let recipes: Recipe[];
    if (localStorage.getItem("allRecipes") == "" || localStorage.getItem("allRecipes") == null) {
      recipes = allRecipes;
      localStorage.setItem("allRecipes", JSON.stringify(recipes)); 
    }
    else {
      recipes = JSON.parse(localStorage.getItem("allRecipes")!);
    }

    this.userRecipes = [];
    this.savedRecipes = [];
    this.recommendedRecipes = [];
    recipes.forEach(recipe => {
      if (recipe.author == this.user.username) this.userRecipes.push(recipe);
    })

    let allFollowings: Following[];
    if (localStorage.getItem("allFollowings") == "" || localStorage.getItem("allFollowings") == null) {
      allFollowings = followings;
      localStorage.setItem("allFollowings", JSON.stringify(allFollowings)); 
    }
    else {
      allFollowings = JSON.parse(localStorage.getItem("allFollowings")!);
    }

    this.following = [];
    allFollowings.forEach(f => {
      if (f.username == this.user.username) this.following.push(f.following);
    })
  }

  change(s: String) {
    this.showed = s;
  }

  recipeRoute(recipe: Recipe) {
    localStorage.setItem("recipe", JSON.stringify(recipe));
    this.router.navigate(["recipe"]);
  }

  userRoute(username: String) {
    let user: User | undefined;
    let users: User[];
    if (localStorage.getItem("allUsers") == "" || localStorage.getItem("allUsers") == null) {
      users = allUsers;
      localStorage.setItem("allUsers", JSON.stringify(users)); 
    }
    else {
      users = JSON.parse(localStorage.getItem("allUsers")!);
    } 
    user = users.find(u => u.username == username);
    localStorage.setItem("userProfile", JSON.stringify(user))
    this.router.navigate(["userProfile"]);
  }

}
