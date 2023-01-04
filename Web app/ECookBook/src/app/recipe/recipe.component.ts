import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Comment } from '../model/comment.model';
import { Recipe } from '../model/recipe.model';
import { ServiceService } from '../service.service';

@Component({
  selector: 'app-recipe',
  templateUrl: './recipe.component.html',
  styleUrls: ['./recipe.component.css']
})
export class RecipeComponent implements OnInit {

  constructor(private service: ServiceService, private router: Router) { }

  recipe: Recipe;
  comments: Comment[];
  saved: boolean;
  username: String;

  ngOnInit(): void {

    // TODO
    this.saved = false;

    if (localStorage.getItem("recipe") == null || localStorage.getItem("recipe") == "") {
      this.router.navigate(["home"]);
    }
    else this.recipe = JSON.parse(localStorage.getItem("recipe")!);

    this.service.getComments(this.recipe.name).subscribe(res => {
      if(res["status"] == 1){
        this.comments = (res["poruka"] as Comment[]);
      }
    });
  }

  // TODO
  save() {
    this.saved = true;
  }

  // TODO
  remove() {
    this.saved = false;
  }

  // TODO
  recommend() {

  }

  // TODO
  visibility(s: String) {

  }

}
