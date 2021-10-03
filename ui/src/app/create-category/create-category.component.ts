import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AppSettings } from '../AppSettings';

@Component({
  selector: 'app-create-category',
  templateUrl: './create-category.component.html',
  styleUrls: ['./create-category.component.scss']
})
export class CreateCategoryComponent implements OnInit {

  categoryForm: FormGroup;

  constructor(private httpClient: HttpClient,
              private snackBar: MatSnackBar) { }

  ngOnInit(): void {
    this.categoryForm = new FormGroup(
      {
        name: new FormControl('', 
          [
            Validators.required, 
            Validators.maxLength(100)
          ]
        ),
        description: new FormControl('', 
          [
            Validators.required,
            Validators.maxLength(255)
          ]
        ),
        image: new FormControl('')
      }
    );
  }

  get name() {
    return this.categoryForm?.get('name');
  }

  get description() {
    return this.categoryForm?.get('description');
  }

  get image() {
    return this.categoryForm?.get('image');
  }

  createCategory(): void {
    const body = {
      name: this.name?.value,
      description: this.description?.value,
      image: this.image?.value
    };
    this.httpClient.post(AppSettings.API_URL + '/category', body).subscribe(
      category => {},
      () => this.snackBar.open('Ups! Coś poszło nie tak!', 'OK', {duration: 3000})
    );
  }

}
