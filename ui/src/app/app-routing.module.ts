import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomepageComponent} from "./homepage/homepage.component";
import {LoginFormComponent} from "./login-form/login-form.component";
import {ProfileComponent} from "./profile/profile.component";
import {RegistrationFormComponent} from "./registration-form/registration-form.component";
import {RecipeComponent} from "./recipe/recipe.component";
import {RegistrationSuccessComponent} from "./registration-success/registration-success.component";
import {CategoryComponent} from "./category/category.component";
import {CreateRecipeComponent} from "./create-recipe/create-recipe.component";
import { CreateCategoryComponent } from './create-category/create-category.component';
import { ManageCategoriesComponent } from './manage-categories/manage-categories.component';
import { UserUpdateComponent } from './user-update/user-update.component';

const routes: Routes = [
  {
    path: '',
    component: HomepageComponent
  },
  {
    path: 'login',
    component: LoginFormComponent
  },
  {
    path: 'profile',
    component: ProfileComponent
  },
  {
    path: 'register',
    component: RegistrationFormComponent
  },
  {
    path: 'register-success',
    component: RegistrationSuccessComponent
  },
  {
    path: 'recipe/:id',
    component: RecipeComponent
  },
  {
    path: 'category/:name',
    component: CategoryComponent
  },
  {
    path: 'create-recipe',
    component: CreateRecipeComponent
  },
  {
    path: 'create-category',
    component: CreateCategoryComponent
  },
  {
    path: 'manage-categories',
    component: ManageCategoriesComponent
  },
  {
    path: 'edit-category/:name',
    component: CreateCategoryComponent
  },
  {
    path: 'update-user',
    component: UserUpdateComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
