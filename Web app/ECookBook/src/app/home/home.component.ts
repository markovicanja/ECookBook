import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../model/user.model';
import { ServiceService } from '../service.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private service: ServiceService, private router: Router) { }

  username: string;
  password: string;

  email: string;
  confPassword: string;

  errorMsg: string;
  successMsg: string;
  res: number;

  loggedUser: User | null;

  ngOnInit(): void {
    if (JSON.parse(localStorage.getItem("user")!) != null) {
      this.router.navigate(["home"]);
    }
    if (localStorage.getItem("user") != null) {
      this.loggedUser = JSON.parse(localStorage.getItem("user")!);
    }
    else {
      this.loggedUser = null;
    }
    
    this.username = "";
    this.email = "";
    this.password = "";
    this.confPassword = "";
    this.errorMsg = "";
    this.successMsg = "";
  }

  reset() {
    this.username = "";
    this.email = "";
    this.password = "";
    this.confPassword = "";
    this.errorMsg = "";
    this.successMsg = "";
  }

  login() {
    this.errorMsg = "";
    if (this.username == "" || this.password == "") {
      this.errorMsg = "You must fill out all the fields.";
      return;
    }
    
    this.service.loginUser(this.username, this.password).subscribe(res => {
      this.res = res["status"];
      if(res["status"] == 1){
        localStorage.setItem('user', JSON.stringify(res["poruka"]));
        // window.location.reload();
      }
      else if(res["status"] == 0){
        this.errorMsg = res["poruka"];
      }
    })
  }

  register() {
    this.errorMsg = "";
    if (this.username == "" || this.email == "" || this.password == "" || this.confPassword == "") {
      this.errorMsg = "You must fill out all the fields.";
      return;
    }
    if (this.password != this.confPassword) {
      this.errorMsg = "Passwords do not match.";
      return;
    }
    this.service.registerUser(this.username, this.email, this.password).subscribe(res => {
      this.res = res["status"];
      if(res["status"] == 1){
        this.errorMsg = res["poruka"];
      }
      else if(res["status"] == 0){
        this.errorMsg = res["poruka"];
      }
    })
  }

}
