import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { comments } from '../data/comments';
import { Comment } from '../model/comment.model';
import { Recipe } from '../model/recipe.model';

@Component({
  selector: 'app-recipe',
  templateUrl: './recipe.component.html',
  styleUrls: ['./recipe.component.css']
})
export class RecipeComponent implements OnInit {

  constructor(private router: Router) { }

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

    let allComments: Comment[];
    if (localStorage.getItem("allComments") == "" || localStorage.getItem("allComments") == null) {
      allComments = comments;
      localStorage.setItem("allComments", JSON.stringify(allComments)); 
    }
    else {
      allComments = JSON.parse(localStorage.getItem("allComments")!);
    }

    this.comments = [];
    allComments.forEach(comment => {
      if (comment.recipe == this.recipe.name) this.comments.push(comment);
    })
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
