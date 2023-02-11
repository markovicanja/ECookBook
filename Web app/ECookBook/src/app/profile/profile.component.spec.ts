import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { Router } from '@angular/router';
import { of } from 'rxjs';
import { Recipe } from '../model/recipe.model';
import { User } from '../model/user.model';

import { ProfileComponent } from './profile.component';

describe('ProfileComponent', () => {
  let component: ProfileComponent;
  let fixture: ComponentFixture<ProfileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule
      ],
      declarations: [ ProfileComponent ]
    })
    .compileComponents();

    let user = new User;
    user.username = "anja";
    user.password = "123";
    user.email = "anja@gmail.com";
    localStorage.setItem("user", JSON.stringify(user));

    fixture = TestBed.createComponent(ProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should return user recipes', fakeAsync(() => {
    let res = { status: 1, poruka: "" };
    spyOn(component.service, 'getUserRecipes').and.returnValue(of(res));
    component.ngOnInit();
    tick();
    expect(component.res).toBe(1);
  }));

  it('should get user recipes', () => {
    let res = {};
    spyOn(component.service, 'getUserRecipes').and.returnValue(of(res));
    component.ngOnInit();
    fixture.detectChanges();
    expect(component.msg).toBe("1");
  });

  it('should get followings', () => {
    let res = {};
    spyOn(component.service, 'getFollowings').and.returnValue(of(res));
    component.ngOnInit();
    fixture.detectChanges();
    expect(component.msg).toBe("2");
  });

  it('should return user followings', fakeAsync(() => {
    let res = { status: 1, poruka: "" };
    spyOn(component.service, 'getFollowings').and.returnValue(of(res));
    component.ngOnInit();
    tick();
    expect(component.res).toBe(1);
  }));

  it('should get all saved recipes', () => {
    let res = {};
    spyOn(component.service, 'getAllSavedRecipes').and.returnValue(of(res));
    component.ngOnInit();
    fixture.detectChanges();
    expect(component.msg).toBe("3");
  });

  it('should return saved recipes', fakeAsync(() => {
    let res = { status: 1, poruka: "" };
    spyOn(component.service, 'getAllSavedRecipes').and.returnValue(of(res));
    component.ngOnInit();
    tick();
    expect(component.res).toBe(1);
  }));

  it('should get all recommended recipes', () => {
    let res = {};
    spyOn(component.service, 'getAllRecommendedRecipes').and.returnValue(of(res));
    component.ngOnInit();
    fixture.detectChanges();
    expect(component.msg).toBe("4");
  });

  it('should return recommended recipes', fakeAsync(() => {
    let res = { status: 1, poruka: "" };
    spyOn(component.service, 'getAllRecommendedRecipes').and.returnValue(of(res));
    component.ngOnInit();
    tick();
    expect(component.res).toBe(1);
  }));

  it('should find user for route', () => {
    let res = {};
    spyOn(component.service, 'findUser').and.returnValue(of(res));
    component.userRoute("anja");
    fixture.detectChanges();
    expect(component.msg).toBe("userRoute");
  });

  it('should change showed layout', () => {
    component.change("all");
    expect(component.showed).toBe("all");
  });

  it('should navigate to recipes', inject([Router], (mockRouter: Router) => {
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

});
