import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { followings } from '../data/following';
import { Following } from '../model/following.model';
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
        console.log(this.userRecipes)
      }
    });

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
    this.service.findUser(username.toString()).subscribe(res => {
      if(res["status"] == 1){
        localStorage.setItem("userProfile", JSON.stringify({'username' : res["username"], 'email' : res["email"]}))
        this.router.navigate(["userProfile"]);
      }
    })
  }

}
