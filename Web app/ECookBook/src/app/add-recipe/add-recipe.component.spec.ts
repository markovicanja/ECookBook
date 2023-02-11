import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { of } from 'rxjs';
import { User } from '../model/user.model';
import { keyable, ServiceService } from '../service.service';

import { AddRecipeComponent } from './add-recipe.component';

describe('AddRecipeComponent', () => {
  let component: AddRecipeComponent;
  let fixture: ComponentFixture<AddRecipeComponent>;
  let service: ServiceService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule
      ],
      providers: [ ServiceService ],
      declarations: [ AddRecipeComponent ]
    })
    .compileComponents();

    let user = new User;
    user.username = "anja";
    user.password = "123";
    user.email = "anja@gmail.com";
    localStorage.setItem("user", JSON.stringify(user));

    fixture = TestBed.createComponent(AddRecipeComponent);
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

  it('should return error', () => {
    component.name = "";
    component.submit();
    expect(component.error).toBe("You must fill out all the fields.");
  });

  it('should add recipe', fakeAsync(() => {
    let res = { status: 1, poruka: "" };
    
    component.name = "Mock recipe";
    component.difficulty = 3;
    component.category = "Breakfast";
    component.cuisine = "american";
    component.img = "img";
    component.description = "Mock recipe description";
    component.user.username = "anja";

    spyOn(service, 'addRecipe').and.returnValue(of(res));
    component.submit();
    tick();
    expect(component.res).toBe(1);
  }));

});