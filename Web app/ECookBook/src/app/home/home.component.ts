import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { allUsers } from '../data/users';
import { User } from '../model/user.model';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private router: Router) { }

  username: string;
  password: string;
  errorMsg: string;
  allUsers: User[];

  ngOnInit(): void {
    if (JSON.parse(localStorage.getItem("user")!) != null) {
      this.router.navigate(["home"]);
    }
    if (localStorage.getItem("allUsers") == "" || localStorage.getItem("allUsers") == null) {
      this.allUsers = allUsers;
      localStorage.setItem("allUsers", JSON.stringify(this.allUsers)); 
    }
    else {
      this.allUsers = JSON.parse(localStorage.getItem("allUsers")!);
    }    
    this.username = "";
    this.password = "";
    this.errorMsg = "";
  }

  reset() {
    this.username = "";
    this.password = "";
    this.errorMsg = "";
  }

  login() {
    this.errorMsg = "";
    if (this.username == "" || this.password == "") {
      this.errorMsg = "Morate popuniti sva polja.";
      return;
    }
    let user = this.allUsers.find(user => user.username == this.username && user.password == this.password);
    if (user == null) {
      this.errorMsg = "Neispravni kredencijali.";
      return;
    }
    localStorage.setItem('user', JSON.stringify(user));
    window.location.reload();
  }


}
