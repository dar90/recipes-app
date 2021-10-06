import { HttpClient } from '@angular/common/http';
import { Component, ElementRef, Input, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatAutocomplete } from '@angular/material/autocomplete';
import { MatOption } from '@angular/material/core';
import { Observable, Subscription } from 'rxjs';
import { AppSettings } from '../AppSettings';
import { AuthService } from '../AuthService';
import { Category } from '../_model/Category';
import { Recipe } from '../_model/Recipe';

@Component({
  selector: 'app-manage-recipe-categories',
  templateUrl: './manage-recipe-categories.component.html',
  styleUrls: ['./manage-recipe-categories.component.scss']
})
export class ManageRecipeCategoriesComponent implements OnInit, OnDestroy {

  @Input() recipe: Recipe;
  categoriesToPick: Category[];
  filteredCategoriesToPick: Category[];

  @ViewChild('categoryInput') categoryInput: ElementRef<HTMLInputElement>;
  @ViewChild('auto') autocomplete: MatAutocomplete;

  role: string;
  private roleSubscription: Subscription;

  constructor(private auth: AuthService,
              private httpClient: HttpClient) { }

  ngOnInit(): void {
    this.roleSubscription = this.auth.currentUser$.subscribe(
      user => this.role = user.role
    );

    this.httpClient.get<Category[]>(AppSettings.API_URL + '/category').subscribe(
      categories => {
        const recipeCategoriesNames: string[] = this.recipe.categories.map(cat => cat.name);
        this.categoriesToPick = categories.filter(cat => !recipeCategoriesNames.includes(cat.name));
        this.filteredCategoriesToPick = this.categoriesToPick;
      }
    );
  }

  ngOnDestroy(): void {
    this.roleSubscription.unsubscribe();
  }

  removeCategory(category: Category): void {
    const recipeCopy: Recipe = JSON.parse(JSON.stringify(this.recipe));
    recipeCopy.categories = recipeCopy.categories.filter(cat => cat.name !== category.name);
    this.updateRecipe(recipeCopy).subscribe(
      updatedRecipe => {
        this.recipe = updatedRecipe; 
        this.categoriesToPick.push(category);
        this.filteredCategoriesToPick = this.categoriesToPick;
      }
    );
  }

  addCategory(category: Category): void {
    const recipeCopy: Recipe = JSON.parse(JSON.stringify(this.recipe));
    recipeCopy.categories.push(category);
    this.updateRecipe(recipeCopy).subscribe(
      updatedRecipe => {
        this.categoriesToPick = this.categoriesToPick.filter(cat => cat.name !== category.name);
        this.filteredCategoriesToPick = this.categoriesToPick;
        this.recipe = updatedRecipe; 
        this.categoryInput.nativeElement.value = '';
      }
    );
  }

  filterAutocomplete(): void {
    const filterValue = this.categoryInput.nativeElement.value;
    this.filteredCategoriesToPick = this.categoriesToPick.filter(cat => cat.name.includes(filterValue));
  }

  private updateRecipe(recipe: Recipe): Observable<Recipe> {
    return this.httpClient.patch<Recipe>(AppSettings.API_URL + '/recipe/' + recipe.id + '/categories', recipe.categories);
  }

}
