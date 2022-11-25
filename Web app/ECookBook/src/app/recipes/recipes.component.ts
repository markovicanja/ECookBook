import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { allRecipes } from '../data/recipes';
import { Recipe } from '../model/recipe.model';

@Component({
  selector: 'app-recipes',
  templateUrl: './recipes.component.html',
  styleUrls: ['./recipes.component.css']
})
export class RecipesComponent implements OnInit {

  constructor(private router: Router) { }

  allRecipes: Recipe[];

  ngOnInit(): void {
    if (localStorage.getItem("allRecipes") == "" || localStorage.getItem("allRecipes") == null) {
      this.allRecipes = allRecipes;
      localStorage.setItem("allRecipes", JSON.stringify(this.allRecipes)); 
    }
    else {
      this.allRecipes = JSON.parse(localStorage.getItem("allRecipes")!);
    }  
  }

  recipeRoute(recipe: Recipe) {

  }

  search() {
    
  }

}
