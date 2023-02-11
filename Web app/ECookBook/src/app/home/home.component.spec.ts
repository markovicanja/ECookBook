import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { of } from 'rxjs';
import { keyable, ServiceService } from '../service.service';

import { HomeComponent } from './home.component';

describe('HomeComponent', () => {
  let component: HomeComponent;
  let fixture: ComponentFixture<HomeComponent>;
  let service: ServiceService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule
      ],
      declarations: [ HomeComponent ],
      providers: [ ServiceService ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    service = TestBed.inject(ServiceService);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should set logged user', () => {
    localStorage.clear();
    component.ngOnInit();
    expect(component.loggedUser).toBe(null);
  });

  it('should reset data', () => {
    component.reset();
    expect(component.username).toBe("");
  });

  it('should call login method', () => {
    fixture.detectChanges();
    spyOn(component, "login");
    let loginButton = fixture.debugElement.query(By.css('#loginButton')).nativeElement;
    loginButton.click();
    expect(component.login).toHaveBeenCalledTimes(1);
  }); 

  it('should return a login error', () => {
    component.username = "";
    component.login();
    expect(component.errorMsg).toBe("You must fill out all the fields.");
  });

  it('should be wrong credentials for login', () => {
    component.username = "fakeUsername";
    component.password = "fakePassword";
    component.login();
    expect(component.errorMsg).toBe("");
  });

  it('should login', fakeAsync(() => {
    let res = { status: 1, poruka: "" };
    component.username = "anja";
    component.password = "123";
    spyOn(service, 'loginUser').and.returnValue(of(res));
    component.login();
    tick();
    expect(component.res).toBe(1);
  }));

  it('should not login', fakeAsync(() => {
    let res = { status: 0, poruka: "" };
    component.username = "fakeUser";
    component.password = "123";
    spyOn(service, 'loginUser').and.returnValue(of(res));
    component.login();
    tick();
    expect(component.res).toBe(0);
  }));

  it('should be a register error', () => {
    component.username = "";
    component.register();
    expect(component.errorMsg).toBe("You must fill out all the fields.");
  });

  it('should be passwords do not match error', () => {
    component.username = "username";
    component.email = "email";
    component.password = "password";
    component.confPassword = "wrongPassword";
    component.register();
    expect(component.errorMsg).toBe("Passwords do not match.");
  });

  it('should register', fakeAsync(() => {
    let res = { status: 1, poruka: "" };
    component.username = "username";
    component.email = "email";
    component.password = "password";
    component.confPassword = "password";
    spyOn(service, 'registerUser').and.returnValue(of(res));
    component.register();
    tick();
    expect(component.res).toBe(1);
  }));

  it('should not register', fakeAsync(() => {
    let res = { status: 0, poruka: "" };
    component.username = "username";
    component.email = "email";
    component.password = "password";
    component.confPassword = "password";
    spyOn(service, 'registerUser').and.returnValue(of(res));
    component.register();
    tick();
    expect(component.res).toBe(0);
  }));

});