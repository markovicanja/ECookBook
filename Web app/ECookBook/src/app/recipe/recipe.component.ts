import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Comment } from '../model/comment.model';
import { Recipe } from '../model/recipe.model';
import { User } from '../model/user.model';
import { ServiceService } from '../service.service';

@Component({
  selector: 'app-recipe',
  templateUrl: './recipe.component.html',
  styleUrls: ['./recipe.component.css']
})
export class RecipeComponent implements OnInit {

  constructor(private service: ServiceService, private router: Router, private datepipe: DatePipe) { }

  user: User;
  recipe: Recipe;
  comments: Comment[];
  saved: boolean;
  username: string; //recommend

  currentComment: string;
  currentRate1: string;
  currentRate2: string;
  currentRate3: string;
  currentRate4: string;
  currentRate5: string;

  recommendErrorMsg: string;
  followings: string[];

  isChangingVisibilityAvailable: boolean;
  visibilityMsg: string;
  visible: number;

  msg: string;
  res: number;

  ngOnInit(): void {
    this.recipe = new Recipe;
    if (localStorage.getItem("user") == null) {
      this.user = new User;
      return;
    }
    else this.user = JSON.parse(localStorage.getItem("user")!);

    this.currentComment = "";
    this.currentRate1 = "";
    this.currentRate2 = "";
    this.currentRate3 = "";
    this.currentRate4 = "";
    this.currentRate5 = "";

    this.visibilityMsg = "";

    this.msg = "";

    this.comments = [];

    if (localStorage.getItem("recipe") == null || localStorage.getItem("recipe") == "") {
      this.recipe = new Recipe;
      this.router.navigate(["home"]);
    }
    else this.recipe = JSON.parse(localStorage.getItem("recipe")!);

    this.service.getComments(this.recipe.name).subscribe(res => {
      this.msg = "get comments";
      if(res["status"] == 1){
        this.comments = (res["poruka"] as Comment[]);
      }
    });
    
    this.recommendErrorMsg = "";
    this.followings = [];
    this.service.getFollowings(this.user.username).subscribe(res => {
      this.msg = "get followings";
      if(res["status"] == 1){
        this.followings = res["poruka"];
      }
    });

    this.saved = false;
    this.service.findIfUserSavedRecipe(this.user.username, this.recipe.name).subscribe(res => {
      this.msg = "findIfUserSavedRecipe";
      if(res["status"] == 1){
        this.saved = true;
      }
    })

    this.isChangingVisibilityAvailable = false;
    if(this.recipe.author === this.user.username){
      this.isChangingVisibilityAvailable = true;
    }
  }
  
  userRoute(username: String) {
    this.msg = "";
    if(username === this.user.username) {
      this.msg = "failed";
      return;
    }
    this.service.findUser(username.toString()).subscribe(res => {
      this.msg = "success";
      if(res["status"] == 1){
        localStorage.setItem("userProfile", JSON.stringify({'username' : res["username"], 'email' : res["email"]}))
        this.router.navigate(["userProfile"]);
      }
    })
  }

  submitComment(){
    const currDate = new Date();
    const date = this.datepipe.transform(currDate, 'dd/MM/yyyy');
    const time = this.datepipe.transform(currDate, 'HH:mm');

    let rate = 0;
    if(this.currentRate1 !== ""){
      rate = 1;
      this.currentRate1 = "";
    }else if(this.currentRate2 !== ""){
      rate = 2;
      this.currentRate2 = "";
    }else if(this.currentRate3 !== ""){
      rate = 3;
      this.currentRate3 = "";
    }else if(this.currentRate4 !== ""){
      rate = 4;
      this.currentRate4 = "";
    }else if(this.currentRate5 !== ""){
      rate = 5;
      this.currentRate5 = "";
    }
    
    this.service.addComment(this.recipe.name, this.user.username, date, time, this.currentComment).subscribe(res => {
      this.msg = "added comment";
      if(res["status"] == 1){
        let newComment = new Comment();
        newComment.recipe = this.recipe.name;
        newComment.author = this.user.username;
        if(date != null) newComment.date = date;
        if(time != null) newComment.time = time;
        newComment.body = this.currentComment;

        this.comments.push(newComment);
        this.currentComment = "";
      }
    });
    if(rate != 0){
      this.service.rateRecipe(this.recipe.name, rate).subscribe(res => {
        this.msg = "rate recipe";
        if(res["status"] == 1){
          this.recipe.rating = res["poruka"];
          localStorage.setItem("recipe", JSON.stringify(this.recipe));
        }
      });
    }
  }

  recommend() {
    this.recommendErrorMsg = "";
    if(!this.followings.includes(this.username)){
      this.recommendErrorMsg = "Username can't be identified.";
      return;
    }
    
    this.service.recommendRecipe(this.user.username, this.username, this.recipe.name).subscribe(res => {
      this.res = res["status"];
      if(res["status"] == 1){
        this.recommendErrorMsg = "Recipe has been recommended successfully.";
      }
    })
  }

  save() {
    this.service.saveRecipe(this.user.username, this.recipe.name).subscribe(res => {
      this.msg = "saved";
      if(res["status"] == 1){
        this.saved = true;
      }
    })
  }

  remove() {    
    this.service.removeSavedRecipe(this.user.username, this.recipe.name).subscribe(res => {
      this.msg = "removed";
      if(res["status"] == 1){
        this.saved = false;
      }
    })
  }

  visibility(s: String) {
    this.visible = 0;
    if(s === "everyone"){
      this.visible = 0;
    } else if(s === "followers"){
      this.visible = 1;
    }else if(s === "hide"){
      this.visible = 2;
    }

    this.service.changeVisiblity(this.recipe.name, this.visible).subscribe(res => {
      this.msg = "visibility change";
      if(res["status"] == 1){
        this.recipe.visibility = this.visible;
        this.visibilityMsg = res["poruka"];  
      }
    })
    this.visibilityMsg = "";  
  }

}
