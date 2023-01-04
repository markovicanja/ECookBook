import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
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

  ngOnInit(): void {
    if (JSON.parse(localStorage.getItem("user")!) != null) {
      this.router.navigate(["home"]);
    }
    
    this.username = "";
    this.email = "";
    this.password = "";
    this.confPassword = "";
    this.errorMsg = "";
  }

  reset() {
    this.username = "";
    this.email = "";
    this.password = "";
    this.confPassword = "";
    this.errorMsg = "";
  }

  login() {
    this.errorMsg = "";
    if (this.username == "" || this.password == "") {
      this.errorMsg = "Morate popuniti sva polja.";
      return;
    }
    
    this.service.loginUser(this.username, this.password).subscribe(res => {
      if(res["status"] == 1){
        localStorage.setItem('user', JSON.stringify(res["poruka"]));
        window.location.reload();
      }
      else if(res["status"] == 0){
        this.errorMsg = res["poruka"];
      }
    })
  }

  register() {
    this.errorMsg = "";
    if (this.username == "" || this.email == "" || this.password == "" || this.confPassword == "") {
      this.errorMsg = "Morate popuniti sva polja.";
      return;
    }
    if (this.password != this.confPassword) {
      this.errorMsg = "Lozinke se razlikuju.";
      return;
    }
    this.service.registerUser(this.username, this.email, this.password).subscribe(res => {
      if(res["status"] == 1){
        this.errorMsg = res["poruka"];
      }
      else if(res["status"] == 0){
        this.errorMsg = res["poruka"];
      }
    })
  }

}
