import { Component, OnInit } from '@angular/core';
import {AuthService} from "../AuthService";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {

  username: string;
  role: string;

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
    this.authService.currentUser$.subscribe(
      user => {
        this.username = user.username;
        this.role = user.role;
      }
    );
  }

  logout() {
    this.authService.logout();
  }

}
