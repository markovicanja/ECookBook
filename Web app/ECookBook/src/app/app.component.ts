import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { User } from './model/user.model';
import { ServiceService } from './service.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'ECookBook';

  constructor(private service: ServiceService, private router: Router) {
  }

  sessionValid: boolean;
  loggedUser: User | null;

  ngOnInit(): void {
    if (localStorage.getItem("user") == null) {
      this.sessionValid = false;
      this.loggedUser = null;
    }
    else {
      this.sessionValid = true;
      this.loggedUser = JSON.parse(localStorage.getItem("user")!);
    }
  }  

  logout() {
    localStorage.removeItem("user");
    this.loggedUser = null;
    this.sessionValid = false;
    this.router.navigate(["home"]);
  }
}
