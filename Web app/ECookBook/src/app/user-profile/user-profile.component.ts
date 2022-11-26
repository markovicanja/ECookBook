import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { allRecipes } from '../data/recipes';
import { Recipe } from '../model/recipe.model';
import { User } from '../model/user.model';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  constructor(private router: Router) { }

  user: User;
  loggedUser: User;
  userRecipes: Recipe[];
  following: boolean;

  ngOnInit(): void {
    // TODO
    this.following = false;

    if (localStorage.getItem("userProfile") == null || localStorage.getItem("userProfile") == "") {
      this.router.navigate(["home"]);
    }
    else this.user = JSON.parse(localStorage.getItem("userProfile")!);

    if (localStorage.getItem("user") == null || localStorage.getItem("user") == "") {
      this.router.navigate(["home"]);
    }
    else this.loggedUser = JSON.parse(localStorage.getItem("user")!);

    let recipes: Recipe[];
    if (localStorage.getItem("allRecipes") == "" || localStorage.getItem("allRecipes") == null) {
      recipes = allRecipes;
      localStorage.setItem("allRecipes", JSON.stringify(recipes)); 
    }
    else {
      recipes = JSON.parse(localStorage.getItem("allRecipes")!);
    }

    this.userRecipes = [];
    recipes.forEach(recipe => {
      if (recipe.author == this.user.username) this.userRecipes.push(recipe);
    })
  }

  // TODO
  follow() {
    this.following = true;
  }

  // TODO
  unfollow() {
    this.following = false;
  }

  recipeRoute(recipe: Recipe) {

  }
}
