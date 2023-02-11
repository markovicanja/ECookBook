import { DatePipe } from '@angular/common';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { of } from 'rxjs';
import { User } from '../model/user.model';
import { keyable, ServiceService } from '../service.service';

import { RecipeComponent } from './recipe.component';

describe('RecipeComponent', () => {
  let component: RecipeComponent;
  let fixture: ComponentFixture<RecipeComponent>;
  let service: ServiceService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule
      ],
      providers: [ DatePipe, ServiceService ],
      declarations: [ RecipeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RecipeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    service = TestBed.inject(ServiceService);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should get comments', fakeAsync(() => {
    let res = { status: 1, poruka: "" };
    spyOn(service, 'getComments').and.returnValue(of(res));
    component.ngOnInit();
    tick();
    expect(component.msg).toBe("get comments");
  }));

  it('should get followings', fakeAsync(() => {
    let res = { status: 1, poruka: "" };
    spyOn(service, 'getFollowings').and.returnValue(of(res));
    component.ngOnInit();
    tick();
    expect(component.msg).toBe("get followings");
  }));

  it('should find if user saved recipe', fakeAsync(() => {
    let res = { status: 1, poruka: "" };
    spyOn(service, 'findIfUserSavedRecipe').and.returnValue(of(res));
    component.ngOnInit();
    tick();
    expect(component.msg).toBe("findIfUserSavedRecipe");
  }));

  it('should not navigate', () => {
    component.user = new User;
    component.user.username = "anja";
    component.userRoute("anja");
    expect(component.msg).toBe("failed");
  });

  it('should navigate', () => {
    let res: keyable = {};
    component.user = new User;
    component.user.username = "anja";
    spyOn(service, 'findUser').and.returnValue(of(res));
    component.userRoute("ogi");
    fixture.detectChanges();
    expect(component.msg).toBe("success");
  });

  it('should set current rate to 1', () => {
    component.currentRate1 = "1";
    component.submitComment();
    expect(component.currentRate1).toBe("");
  });

  it('should set current rate to 2', () => {
    component.currentRate1 = "";
    component.currentRate2 = "2";
    component.submitComment();
    expect(component.currentRate2).toBe("");
  });

  it('should set current rate to 3', () => {
    component.currentRate1 = "";
    component.currentRate2 = "";
    component.currentRate3 = "3";
    component.submitComment();
    expect(component.currentRate3).toBe("");
  });

  it('should set current rate to 4', () => {
    component.currentRate1 = "";
    component.currentRate2 = "";
    component.currentRate3 = "";
    component.currentRate4 = "4";
    component.submitComment();
    expect(component.currentRate4).toBe("");
  });

  it('should set current rate to 5', () => {
    component.currentRate1 = "";
    component.currentRate2 = "";
    component.currentRate3 = "";
    component.currentRate4 = "";
    component.currentRate5 = "5";
    component.submitComment();
    expect(component.currentRate5).toBe("");
  });

  it('should add comment', fakeAsync(() => {
    let res = { status: 1, poruka: "" };
    spyOn(service, 'addComment').and.returnValue(of(res));
    component.submitComment();
    tick();
    expect(component.msg).toBe("added comment");
  }));

  it('should rate recipe', fakeAsync(() => {
    let res = { status: 1, poruka: "" };
    spyOn(service, 'rateRecipe').and.returnValue(of(res));
    component.currentRate1 = "1";
    component.submitComment();
    tick();
    expect(component.msg).toBe("rate recipe");
  }));

  it('should not allow recommendation', () => {
    component.username = "anja";
    component.followings = ["nina", "ogi"];
    component.recommend();
    expect(component.recommendErrorMsg).toBe("Username can't be identified.");
  });

  it('should allow recommendation', fakeAsync(() => {
    let res = { status: 1, poruka: "" };
    component.username = "anja";
    component.followings = ["anja", "ogi"];
    spyOn(service, 'recommendRecipe').and.returnValue(of(res));
    component.recommend();
    tick();
    expect(component.res).toBe(1);
  }));

  it('should save recipe', () => {
    let res: keyable = {};
    component.user.username = "anja";
    component.recipe.name = "pancakes";
    spyOn(service, 'saveRecipe').and.returnValue(of(res));
    component.save();
    fixture.detectChanges();
    expect(component.msg).toBe("saved");
  });

  it('should remove saved recipe', () => {
    let res: keyable = {};
    component.user.username = "anja";
    component.recipe.name = "pancakes";
    spyOn(service, 'removeSavedRecipe').and.returnValue(of(res));
    component.remove();
    fixture.detectChanges();
    expect(component.msg).toBe("removed");
  });

  it('should set visibility to everyone', () => {
    component.visibility("everyone");
    expect(component.visible).toBe(0);
  });

  it('should set visibility to followers', () => {
    component.visibility("followers");
    expect(component.visible).toBe(1);
  });

  it('should hide visibility', () => {
    component.visibility("hide");
    expect(component.visible).toBe(2);
  });

  it('should change visibility', () => {
    let res: keyable = {};
    component.recipe.name = "pancakes";
    spyOn(service, 'changeVisiblity').and.returnValue(of(res));
    component.visibility("hide");
    fixture.detectChanges();
    expect(component.msg).toBe("visibility change");
  });
  

});
