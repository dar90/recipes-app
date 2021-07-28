import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {Ingredient} from "../_model/Ingredient";
import {HttpClient} from "@angular/common/http";
import {AppSettings} from "../AppSettings";
import {Category} from "../_model/Category";

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

  constructor(private httpClient: HttpClient) { }

  ngOnInit(): void {
    this.httpClient.get<Category[]>(AppSettings.API_URL + '/category').subscribe(
      response => this.allCategories = response
    );

    this.firstForm = new FormGroup({
      title: new FormControl(''),
      time: new FormControl(''),
      portions: new FormControl(''),
      difficulty: new FormControl('')
    });

    this.secondForm = new FormGroup({
      ingredient: new FormControl(''),
      amount: new FormControl(''),
      measureUnit: new FormControl('')
    });

    this.thirdForm = new FormGroup({
      image: new FormControl(''),
      categories: new FormControl(''),
      description: new FormControl('')
    });
  }

  addIngredient() {
    const name: string = this.secondForm?.get('ingredient')?.value;
    const amount: number = this.secondForm?.get('amount')?.value;
    const measureUnit: string = this.secondForm?.get('measureUnit')?.value;
    const ingredient: Ingredient = {amount: amount, id: 0, measureUnit: measureUnit, name: name, recipe: undefined};
    this.recipeIngredients.push(ingredient);
    this.secondForm.reset();
  }

  removeIngredient(ingredient: Ingredient) {
    this.recipeIngredients = this.recipeIngredients.filter(i => i.measureUnit !== ingredient.measureUnit ||
                                                      i.name !== ingredient.name ||
                                                      i.amount !== ingredient.amount);
  }

}
