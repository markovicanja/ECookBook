import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../model/user.model';
import { ServiceService } from '../service.service';

@Component({
  selector: 'app-add-recipe',
  templateUrl: './add-recipe.component.html',
  styleUrls: ['./add-recipe.component.css']
})
export class AddRecipeComponent implements OnInit {

  constructor(private service: ServiceService, private router: Router) { }

  user: User;
  name: string;
  difficulty: number;
  category: string;
  categories: string[];
  cuisine: string;
  img: string;
  description: string;
  error: string;

  ngOnInit(): void {
    if (localStorage.getItem("user") == null) return;
    else this.user = JSON.parse(localStorage.getItem("user")!);

    this.error = "";
    this.categories = [
      "Breakfast", "Dessert", "Dinner", "Salad"
    ];

    this.name = "";
    this.category = this.categories[0];
    this.cuisine == "";
    this.img == "";
    this.description == "";
  }

  submit(){
    this.error = "";
    if (this.name == "" || this.difficulty == undefined || this.cuisine == "" || this.img == "" || this.description == "") {
      this.error = "You must fill out all the fields.";
      return;
    }

    const rating = 3;
    const visibility = 0;
    this.service.addRecipe(this.name, this.difficulty, this.category, this.cuisine, this.img, this.description, this.user.username, visibility, rating).subscribe(res => {
      this.error = res["poruka"];
    });
  }

}
