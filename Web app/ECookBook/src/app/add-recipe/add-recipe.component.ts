import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-recipe',
  templateUrl: './add-recipe.component.html',
  styleUrls: ['./add-recipe.component.css']
})
export class AddRecipeComponent implements OnInit {

  constructor(private router: Router) { }

  name: string;
  difficulty: number;
  category: string;
  cuisine: string;
  img: string;
  description: string;
  error: string;

  ngOnInit(): void {
    
  }

}
