import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Recipe } from '../model/recipe.model';
import { ServiceService } from '../service.service';

@Component({
  selector: 'app-recipes',
  templateUrl: './recipes.component.html',
  styleUrls: ['./recipes.component.css']
})
export class RecipesComponent implements OnInit {

  constructor(private service: ServiceService, private router: Router) { }

  allRecipes: Recipe[];

  ngOnInit(): void {
    this.service.getAllRecipes().subscribe(res => {
      if(res["status"] == 1){
        this.allRecipes = res["poruka"];
      }
    });
  }

  recipeRoute(recipe: Recipe) {
    localStorage.setItem("recipe", JSON.stringify(recipe));
    this.router.navigate(["recipe"]);
  }

  search() {
    
  }

}
