import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomepageComponent} from "./homepage/homepage.component";
import {LoginFormComponent} from "./login-form/login-form.component";
import {ProfileComponent} from "./profile/profile.component";
import {RegistrationFormComponent} from "./registration-form/registration-form.component";
import {RecipeComponent} from "./recipe/recipe.component";

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
    path: 'recipe/:id',
    component: RecipeComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
