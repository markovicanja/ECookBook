import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { allRecipes } from '../data/recipes';
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
  }

  change(s: String) {
    this.showed = s;
  }

  recipeRoute(recipe: Recipe) {

  }

}
