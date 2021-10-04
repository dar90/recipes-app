import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { AppSettings } from '../AppSettings';
import { Category } from '../_model/Category';

@Component({
  selector: 'app-create-category',
  templateUrl: './create-category.component.html',
  styleUrls: ['./create-category.component.scss']
})
export class CreateCategoryComponent implements OnInit {

  categoryForm: FormGroup;

  category: Category;

  constructor(private httpClient: HttpClient,
              private snackBar: MatSnackBar,
              private route: ActivatedRoute,
              private router: Router) { }

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

    this.route.params.subscribe(
      params => {
        if(params['name']) {
          this.httpClient.get<Category>(AppSettings.API_URL + '/category/' + params['name']).subscribe(
            category => {
              this.category = category;
              this.categoryForm.setValue({
                name: category.name,
                description: category.description,
                image: category.image
              });
            }
          );
        }
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

    if(this.category) {

      body.name = this.category.name;
      this.httpClient.put(AppSettings.API_URL + '/category', body).subscribe(
        () => {
          this.snackBar.open('Pomyślnie zapisano zmiany!', 'OK', {duration: 3000});
          this.router.navigate(['/manage-categories']);
        },
        () => this.snackBar.open('Ups! Coś poszło nie tak!', 'OK', {duration: 3000})
      );

    } else {

      this.httpClient.post(AppSettings.API_URL + '/category', body).subscribe(
        () => {
          this.snackBar.open('Kategoria została utworzona!', 'OK', {duration: 3000});
          this.router.navigate(['/manage-categories']);
        },
        err => {
          if(err?.status === 409)
            this.snackBar.open('Nazwa kategorii jest już zajęta!', 'OK', {duration: 3000});
          else
            this.snackBar.open('Ups! Coś poszło nie tak!', 'OK', {duration: 3000});
        } 
      );

    }
  }

}
