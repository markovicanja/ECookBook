import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, fakeAsync, inject, TestBed, tick } from '@angular/core/testing';
import { Router } from '@angular/router';
import { of } from 'rxjs';
import { Recipe } from '../model/recipe.model';
import { User } from '../model/user.model';
import { keyable, ServiceService } from '../service.service';

import { UserProfileComponent } from './user-profile.component';

describe('UserProfileComponent', () => {
  let component: UserProfileComponent;
  let fixture: ComponentFixture<UserProfileComponent>;
  let service: ServiceService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule
      ],
      declarations: [ UserProfileComponent ],
      providers: [ ServiceService ]
    })
    .compileComponents();

    let user = new User;
    user.username = "anja";
    user.password = "123";
    user.email = "anja@gmail.com";
    localStorage.setItem("user", JSON.stringify(user));

    let userProfile = new User;
    user.username = "ogi";
    user.password = "123";
    user.email = "ogi@gmail.com";
    localStorage.setItem("userProfile", JSON.stringify(userProfile));

    fixture = TestBed.createComponent(UserProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    service = TestBed.inject(ServiceService);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should return user recipes', fakeAsync(() => {
    let res = { status: 1, poruka: "" };
    spyOn(service, 'getUserRecipes').and.returnValue(of(res));
    component.ngOnInit();
    tick();
    expect(component.res).toBe(1);
  }));

  it('should return following status', fakeAsync(() => {
    let res = { status: 1, poruka: "" };
    spyOn(service, 'getIsFollowing').and.returnValue(of(res));
    component.ngOnInit();
    tick();
    expect(component.res).toBe(1);
  }));

  it('should follow', () => {
    let res: keyable = {};
    spyOn(service, 'setIsFollowing').and.returnValue(of(res));
    component.follow();
    fixture.detectChanges();
    expect(component.msg).toBe("follow");
  });

  it('should return follow status', fakeAsync(() => {
    let res = { status: 1, poruka: "" };
    spyOn(service, 'setIsFollowing').and.returnValue(of(res));
    component.follow();
    tick();
    expect(component.res).toBe(1);
  }));

  it('should unfollow', () => {
    let res: keyable = {};
    spyOn(service, 'unfollow').and.returnValue(of(res));
    component.unfollow();
    fixture.detectChanges();
    expect(component.msg).toBe("unfollow");
  });

  it('should return unfollow status', fakeAsync(() => {
    let res = { status: 1, poruka: "" };
    spyOn(service, 'unfollow').and.returnValue(of(res));
    component.unfollow();
    tick();
    expect(component.res).toBe(1);
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

});
