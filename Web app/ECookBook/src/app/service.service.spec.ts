import { TestBed } from '@angular/core/testing';

import { HttpClientTestingModule } from '@angular/common/http/testing';

import { keyable, ServiceService } from './service.service';
import { User } from './model/user.model';
import { of } from 'rxjs';
import { Recipe } from './model/recipe.model';

describe('ServiceService', () => {
  let service: ServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule]
    });
    service = TestBed.inject(ServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get all users', () => {
    let response: User[] = [];
    service.getAllUsers().subscribe(res => {
      response = res;
    });
    expect(response).toEqual([]);
  });

  it('should get all recipes', () => {
    let response: keyable = {};
    service.getAllRecipes().subscribe(res => {
      response = res;
    });
    expect(response).toEqual({});
  });

  it('should register user', () => {
    let username = "anja";
    let password = "123";
    let email = "anja@gmail.com";
    let response: keyable = {};
    service.registerUser(username, email, password).subscribe(res => {
      response = res;
    });
    expect(response).toEqual({});
  });

  it('should find user', () => {
    let response: keyable = {};
    service.findUser("anja").subscribe(res => {
      response = res;
    });
    expect(response).toEqual({});
  });

  it('should add recipe', () => {
    let recipe = new Recipe();
    recipe.name = "Cookies";
    recipe.author = "anja";
    recipe.category = "Dessert";
    recipe.cuisine = "american";
    recipe.description = "Yummy cookies recipe";
    recipe.difficulty = 3;
    recipe.img = "cookies";
    recipe.rating = 4;
    recipe.visibility = 1;
    
    let response: keyable = {};
    service.addRecipe(recipe.name, recipe.difficulty, recipe.category, recipe.cuisine, recipe.img,
      recipe.description, recipe.author, recipe.visibility, recipe.rating).subscribe(res => {
      response = res;
    });
    expect(response).toEqual({});
  });

  it('should recommend recipe', () => {
    let response: keyable = {};
    service.recommendRecipe("anja", "ogi", "Cookies").subscribe(res => {
      response = res;
    });
    expect(response).toEqual({});
  });

  it('should save recipe', () => {
    let response: keyable = {};
    service.saveRecipe("anja", "Cookies").subscribe(res => {
      response = res;
    });
    expect(response).toEqual({});
  });

  it('should remove saved recipe', () => {
    let response: keyable = {};
    service.removeSavedRecipe("anja", "Cookies").subscribe(res => {
      response = res;
    });
    expect(response).toEqual({});
  });

  it('should set following status to true', () => {
    let response: keyable = {};
    service.setIsFollowing("anja", "ogi").subscribe(res => {
      response = res;
    });
    expect(response).toEqual({});
  });

  it('should set following status to false', () => {
    let response: keyable = {};
    service.unfollow("anja", "ogi").subscribe(res => {
      response = res;
    });
    expect(response).toEqual({});
  });

});
