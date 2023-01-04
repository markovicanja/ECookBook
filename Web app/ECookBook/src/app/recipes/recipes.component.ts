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

  queriedRecipes: Recipe[];
  searchQuery: string;

  sorter: string;

  selectedCategory: string;

  ngOnInit(): void {
    this.searchQuery = "";
    this.sorter = "Sort";
    this.selectedCategory = "All";
    this.service.getAllRecipes().subscribe(res => {
      if(res["status"] == 1){
        this.allRecipes = res["poruka"];
        this.queriedRecipes = this.allRecipes;
      }
    });
  }

  recipeRoute(recipe: Recipe) {
    localStorage.setItem("recipe", JSON.stringify(recipe));
    this.router.navigate(["recipe"]);
  }

  search(){
    const stringToDetect = this.searchQuery.toLowerCase();
    this.queriedRecipes = this.allRecipes.filter(recipe => recipe.name.toLowerCase().includes(stringToDetect));

    this.sort();
  }

  sort(){
    this.sortByCat();
    if(this.sorter !== "Sort") this.sortByNum();
  }

  sortByCat(){
    if(this.selectedCategory !== "All"){
      this.queriedRecipes = this.queriedRecipes.filter(recipe => recipe.category === this.selectedCategory);
    }
  }
  
  sortByNum(){
    if(this.sorter === "sort-diff-asc") this.queriedRecipes.sort((a, b) => a.difficulty - b.difficulty);
    if(this.sorter === "sort-diff-desc") this.queriedRecipes.sort((a, b) => b.difficulty - a.difficulty);
    if(this.sorter === "sort-rtg-asc") this.queriedRecipes.sort((a, b) => a.rating - b.rating);
    if(this.sorter === "sort-rtg-desc") this.queriedRecipes.sort((a, b) => b.rating - a.rating);
  }
  
  categorySelected(newCategory: string){
    this.selectedCategory = newCategory;

    this.search();
  }

  onKeyUp(x : Event) {
    this.search();
  }

  onOptionSelect(x : Event) {
    this.sort();
  }
}
