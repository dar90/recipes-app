import { HttpClient } from '@angular/common/http';
import { Component, HostListener, OnInit } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AppSettings } from '../AppSettings';

@Component({
  selector: 'app-user-update',
  templateUrl: './user-update.component.html',
  styleUrls: ['./user-update.component.scss']
})
export class UserUpdateComponent implements OnInit {

  passwordForm: FormGroup;
  emailForm: FormGroup;

  appWidth: number;

  constructor(private httpClient: HttpClient,
              private snackBar: MatSnackBar) {}

  ngOnInit(): void {
    this.appWidth = window.innerWidth;
    this.passwordForm = new FormGroup({
      oldPassword: new FormControl('', [
        Validators.required
      ]),
      newPassword: new FormControl('', [
        Validators.required,
        Validators.minLength(5),
        Validators.maxLength(55)
      ]),
      repeatedNewPassword: new FormControl('', [
        Validators.required,
        Validators.minLength(5),
        Validators.maxLength(55),
        this.equalPasswordsValidator()
      ])
    });

    this.emailForm = new FormGroup({
      newEmail: new FormControl('', [
        Validators.required,
        Validators.email
      ]),
      password: new FormControl('', [
        Validators.required
      ])
    });
  }

  get password() {
    return this.emailForm?.get('password');
  }

  get newPassword() {
    return this.passwordForm?.get('newPassword');
  }

  get repeatedNewPassword() {
    return this.passwordForm?.get('repeatedNewPassword');
  }

  get oldPassword() {
    return this.passwordForm?.get('oldPassword');
  }

  get newEmail() {
    return this.emailForm?.get('newEmail');
  }

  private equalPasswordsValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null =>
      this.newPassword?.value !== this.repeatedNewPassword?.value ? { equalPasswords: { value: control.value } } : null;
  }

  changePassword(): void {
    const body = {
      oldPassword: this.oldPassword?.value,
      newPassword: this.newPassword?.value,
      repeatedNewPassword: this.repeatedNewPassword?.value
    };
    this.httpClient.patch(AppSettings.API_URL + '/user/password', body).subscribe(
      () => this.snackBar.open('Hasło zostało zmienione', 'OK', {duration: 3000}),
      () => this.snackBar.open('Nie udało się zmienić hasła!', 'OK', {duration: 3000})
    );
  }

  changeEmail(): void {
    const body = {
      password: this.password?.value,
      newEmail: this.newEmail?.value
    };
    this.httpClient.patch(AppSettings.API_URL + '/user/email', body).subscribe(
       () => this.snackBar.open('Adres email został zmieniony', 'OK', {duration: 3000}),
      err => {
        if(err?.status === 409)
          this.snackBar.open('Wybrany adres email jest zajęty!', 'OK', {duration: 3000});
        else
          this.snackBar.open('Nie udało się zmienić adresu email!', 'OK', {duration: 3000});
      }
    );
  }

  @HostListener('window:resize')
  updateWidth() {
    this.appWidth = window.innerWidth;
  }
}
