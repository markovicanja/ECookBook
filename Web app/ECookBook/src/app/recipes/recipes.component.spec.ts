import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, fakeAsync, TestBed, tick, inject } from '@angular/core/testing';
import { Router } from '@angular/router';
import { of } from 'rxjs';
import { Recipe } from '../model/recipe.model';
import { User } from '../model/user.model';
import { ServiceService } from '../service.service';

import { RecipesComponent } from './recipes.component';

describe('RecipesComponent', () => {
  let component: RecipesComponent;
  let fixture: ComponentFixture<RecipesComponent>;
  let service: ServiceService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule
      ],
      declarations: [ RecipesComponent ],
      providers: [ ServiceService ]
    })
    .compileComponents();

    let user = new User;
    user.username = "anja";
    user.password = "123";
    user.email = "anja@gmail.com";
    localStorage.setItem("user", JSON.stringify(user));

    fixture = TestBed.createComponent(RecipesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    service = TestBed.inject(ServiceService);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should return', () => {
    localStorage.clear();
    expect(component.ngOnInit()).toBe(undefined);
  });

  it('should set sorter', () => {
    expect(component.sorter).toBe("Sort");
  });

  it('should set user', () => {
    expect(component.user.username).toBe("anja");
  });

  it('should set message', () => {
    expect(component.msg).toBe("");
  });

  it('should get all recipes by visiblity', fakeAsync(() => {
    let res = { status: 1, poruka: "" };
    spyOn(service, 'getAllRecipesByVisibility').and.returnValue(of(res));
    component.ngOnInit();
    tick();
    expect(component.msg).toBe("getAllRecipesByVisibility");
  }));

  it('should navigate to recipe', inject([Router], (mockRouter: Router) => {
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

    const spy = spyOn(mockRouter, 'navigate').and.stub();
    component.recipeRoute(recipe);
  
    expect(spy.calls.first().args[0]).toContain('recipe');
  }));

  it('should sort searched recipes', () => {
    spyOn(component, "sort");
    component.search();
    expect(component.sort).toHaveBeenCalledTimes(1);
  });

  it('should call sort by category', () => {
    spyOn(component, "sortByCat");
    component.sort();
    expect(component.sortByCat).toHaveBeenCalledTimes(1);
  });

  it('should sort by rating', () => {
    spyOn(component, "sortByNum");
    component.sorter = "sorter";
    component.sort();
    expect(component.sortByNum).toHaveBeenCalledTimes(1);
  });

  it('should change selected category', () => {
    component.categorySelected("Breakfast");
    expect(component.selectedCategory).toBe("Breakfast");
  });

  it('should call search', () => {
    spyOn(component, "search");
    const event = new Event('click');
    component.onKeyUp(event);
    expect(component.search).toHaveBeenCalledTimes(1);
  });

  it('should call search', () => {
    spyOn(component, "sort");
    const event = new Event('click');
    component.onOptionSelect(event);
    expect(component.sort).toHaveBeenCalledTimes(1);
  });

  it('should sort by difficulty in ascending order', () => {
    spyOn(component.queriedRecipes, "sort");
    component.sorter = "sort-diff-asc";
    component.sortByNum();
    expect(component.queriedRecipes.sort).toHaveBeenCalledTimes(1);
  });

  it('should sort by difficulty in descending order', () => {
    spyOn(component.queriedRecipes, "sort");
    component.sorter = "sort-diff-desc";
    component.sortByNum();
    expect(component.queriedRecipes.sort).toHaveBeenCalledTimes(1);
  });

  it('should sort by rating in ascending order', () => {
    spyOn(component.queriedRecipes, "sort");
    component.sorter = "sort-rtg-asc";
    component.sortByNum();
    expect(component.queriedRecipes.sort).toHaveBeenCalledTimes(1);
  });

  it('should sort by rating in descending order', () => {
    spyOn(component.queriedRecipes, "sort");
    component.sorter = "sort-rtg-desc";
    component.sortByNum();
    expect(component.queriedRecipes.sort).toHaveBeenCalledTimes(1);
  });

});
