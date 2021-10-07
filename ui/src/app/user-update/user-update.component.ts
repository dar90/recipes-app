import { Component, HostListener, OnInit } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';

@Component({
  selector: 'app-user-update',
  templateUrl: './user-update.component.html',
  styleUrls: ['./user-update.component.scss']
})
export class UserUpdateComponent implements OnInit {

  passwordForm: FormGroup;
  emailForm: FormGroup;

  appWidth: number;

  constructor() {}

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
    console.log('change password');
  }

  changeEmail(): void {
    console.log('change email');
  }

  @HostListener('window:resize')
  updateWidth() {
    this.appWidth = window.innerWidth;
  }
}
