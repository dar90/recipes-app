import { Component, OnInit } from '@angular/core';
import {AbstractControl, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {AppSettings} from "../AppSettings";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Router} from "@angular/router";

@Component({
  selector: 'app-registration-form',
  templateUrl: './registration-form.component.html',
  styleUrls: ['./registration-form.component.scss']
})
export class RegistrationFormComponent implements OnInit {
  registerForm: FormGroup;

  constructor(private httpClient: HttpClient,
              private snackBar: MatSnackBar,
              private router: Router) { }

  ngOnInit(): void {
    this.registerForm = new FormGroup({
      login: new FormControl('', [
        Validators.required,
        Validators.minLength(5),
        Validators.maxLength(25)
      ]),
      password: new FormControl('', [
        Validators.required,
        Validators.minLength(5),
        Validators.maxLength(55)
      ]),
      repeatedPassword: new FormControl('', [
        Validators.required,
        Validators.minLength(5),
        Validators.maxLength(55),
        this.equalPasswordsValidator()
      ]),
      email: new FormControl('', [
        Validators.required,
        Validators.email
      ])
    });
  }

  register() {
    const body = {
      login: this.login?.value,
      password: this.password?.value,
      repeatedPassword: this.repeatedPassword?.value,
      email: this.email?.value
    };
    this.httpClient.post(AppSettings.API_URL + '/user/register', body).subscribe(
      () => this.router.navigate(['/register-success']),
      () => this.snackBar.open('Rejestracja nie powiodła się!', 'OK', {duration: 3000})
    );
  }

  get login() {
    return this.registerForm?.get('login');
  }

  get password() {
    return this.registerForm?.get('password');
  }

  get repeatedPassword() {
    return this.registerForm?.get('repeatedPassword');
  }

  get email() {
    return this.registerForm?.get('email');
  }

  private equalPasswordsValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null =>
      this.password?.value !== this.repeatedPassword?.value ? { equalPasswords: { value: control.value } } : null;
  }

}
