import {Component, OnDestroy, OnInit} from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import {AuthService} from "../AuthService";
import {Router} from "@angular/router";
import {Subscription} from "rxjs";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.scss']
})
export class LoginFormComponent implements OnInit, OnDestroy {

  loginForm: FormGroup;
  role: string;

  private subs: Subscription[] = [];

  constructor(private authService: AuthService,
              private router: Router,
              private snackBar: MatSnackBar) { }

  ngOnInit(): void {
    const authSub = this.authService.currentUser$.subscribe(
      user => this.role = user.role
    );
    this.subs.push(authSub);

    const errorsSub = this.authService.loginErrors$.subscribe(
      message => this.snackBar.open(message, 'OK')
    );
    this.subs.push(errorsSub);

    if(this.role === 'NOT_LOGGED_IN') {
      this.loginForm = new FormGroup({
        username: new FormControl(''),
        password: new FormControl('')
      });
    } else {
      this.router.navigate(['']);
    }
  }

  ngOnDestroy(): void {
    this.subs.forEach(sub => sub.unsubscribe());
  }

  login() {
    this.authService.login(
      this.loginForm.get('username')?.value,
      this.loginForm.get('password')?.value,
    );
  }

}
