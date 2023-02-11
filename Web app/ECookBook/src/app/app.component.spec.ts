import { HttpClientTestingModule } from '@angular/common/http/testing';
import { inject, TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { User } from './model/user.model';

describe('AppComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        HttpClientTestingModule
      ],
      declarations: [
        AppComponent
      ],
    }).compileComponents();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it(`should have as title 'ECookBook'`, () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app.title).toEqual('ECookBook');
  });

  it('should render title', () => {
    const fixture = TestBed.createComponent(AppComponent);
    fixture.detectChanges();
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('.navbar-brand')?.textContent).toContain('ECookBook');
  });

  it('should be invalid session', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;

    localStorage.clear();

    app.ngOnInit();
    fixture.detectChanges();
    expect(app.sessionValid).toBeFalse();
  });

  it('should be valid session', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;

    let user = new User;
    user.username = "anja";
    user.password = "123";
    user.email = "anja@gmail.com";
    localStorage.setItem("user", JSON.stringify(user));

    app.ngOnInit();
    fixture.detectChanges();
    expect(app.sessionValid).toBeTrue();
  });

  it('should navigate to home', inject([Router], (mockRouter: Router) => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    fixture.detectChanges();
    const spy = spyOn(mockRouter, 'navigate').and.stub();
    app.logout();
    expect(spy.calls.first().args[0]).toContain('home');
  }));

});
