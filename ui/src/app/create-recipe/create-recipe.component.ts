import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, FormGroupDirective, Validators} from "@angular/forms";
import {Ingredient} from "../_model/Ingredient";
import {HttpClient} from "@angular/common/http";
import {AppSettings} from "../AppSettings";
import {Category} from "../_model/Category";
import {Recipe} from "../_model/Recipe";
import {Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-create-recipe',
  templateUrl: './create-recipe.component.html',
  styleUrls: ['./create-recipe.component.scss']
})
export class CreateRecipeComponent implements OnInit {

  firstForm: FormGroup;
  secondForm: FormGroup;
  thirdForm: FormGroup;

  recipeIngredients: Ingredient[] = [];

  allCategories: Category[];

  constructor(private httpClient: HttpClient,
              private router: Router,
              private snackBar: MatSnackBar) { }

  ngOnInit(): void {
    this.httpClient.get<Category[]>(AppSettings.API_URL + '/category').subscribe(
      response => this.allCategories = response
    );

    this.firstForm = new FormGroup({
      title: new FormControl('', [
        Validators.required,
        Validators.maxLength(255)
      ]),
      time: new FormControl('', [
        Validators.required,
        Validators.min(5)
      ]),
      portions: new FormControl('', [
        Validators.required,
        Validators.min(1),
        Validators.max(100)
      ]),
      difficulty: new FormControl('EASY', [
        Validators.required
      ])
    });

    this.secondForm = new FormGroup({
      ingredient: new FormControl('', [
        Validators.required,
        Validators.maxLength(255)
      ]),
      amount: new FormControl('', [
        Validators.required,
        Validators.min(0.01)
      ]),
      measureUnit: new FormControl('', [
        Validators.required,
        Validators.maxLength(100)
      ])
    });

    this.thirdForm = new FormGroup({
      image: new FormControl('', [
        Validators.required
      ]),
      categories: new FormControl('', [
        Validators.required
      ]),
      description: new FormControl('', [
        Validators.required,
        Validators.minLength(20)
      ])
    });
  }

  addIngredient(formDirective: FormGroupDirective) {
    if(!this.secondForm.valid)
      return;

    const name: string = this.secondForm?.get('ingredient')?.value;
    const amount: number = this.secondForm?.get('amount')?.value;
    const measureUnit: string = this.secondForm?.get('measureUnit')?.value;
    const ingredient: Ingredient = {amount: amount, id: 0, measureUnit: measureUnit, name: name, recipe: undefined};
    this.recipeIngredients.push(ingredient);
    formDirective.resetForm();
  }

  removeIngredient(ingredient: Ingredient) {
    this.recipeIngredients = this.recipeIngredients.filter(i => i.measureUnit !== ingredient.measureUnit ||
                                                      i.name !== ingredient.name ||
                                                      i.amount !== ingredient.amount);
  }

  createRecipe() {
    const categories: Category[] = this.categories?.value.map((categoryName: string) => {
      return {
        name: categoryName,
        description: "",
        image: "",
        recipes: []
      };
    })

    const recipe: Recipe = {
      author: undefined,
      categories: categories,
      description: this.description?.value,
      difficulty: this.difficulty?.value,
      id: 0,
      image: this.image?.value,
      ingredients: this.recipeIngredients,
      opinions: [],
      portions: this.portions?.value,
      time: this.time?.value,
      title: this.title?.value
    };

    this.httpClient.post<Recipe>(AppSettings.API_URL + '/recipe', recipe).subscribe(
      response => this.router.navigate(['/recipe/' + response.id]),
      () => this.snackBar.open('Dodawanie przepisu nie powiodło się!', 'OK', {duration: 3000})
    );
  }

  get title() {
    return this.firstForm?.get('title');
  }

  get time() {
    return this.firstForm?.get('time');
  }

  get portions() {
    return this.firstForm?.get('portions');
  }

  get difficulty() {
    return this.firstForm?.get('difficulty');
  }

  get ingredient() {
    return this.secondForm?.get('ingredient');
  }

  get amount() {
    return this.secondForm?.get('amount');
  }

  get measureUnit() {
    return this.secondForm?.get('measureUnit');
  }

  get image() {
    return this.thirdForm?.get('image');
  }

  get categories() {
    return this.thirdForm?.get('categories');
  }

  get description() {
    return this.thirdForm?.get('description');
  }

}
