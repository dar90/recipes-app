import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LayoutModule } from '@angular/cdk/layout';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { LoginFormComponent } from './login-form/login-form.component';
import { HomepageComponent } from './homepage/homepage.component';
import { RegistrationFormComponent } from './registration-form/registration-form.component';
import { ProfileComponent } from './profile/profile.component';
import { ReactiveFormsModule } from '@angular/forms';
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import { NavbarComponent } from './navbar/navbar.component';
import {MatCardModule} from "@angular/material/card";
import {AuthService} from "./AuthService";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {RecipeCardComponent} from "./recipe-card/recipe-card.component";
import {UserStatsCardComponent} from "./user-stats-card/user-stats-card.component";
import {OpinionCardComponent} from "./opinion-card/opinion-card.component";
import {AuthInterceptor} from "./AuthInterceptor";
import { RecipeComponent } from './recipe/recipe.component';
import {RegistrationSuccessComponent} from "./registration-success/registration-success.component";
import { CategoryCardComponent } from './category-card/category-card.component';
import { RecipesListComponent } from './recipes-list/recipes-list.component';
import { CategoryComponent } from './category/category.component';
import { CreateRecipeComponent } from './create-recipe/create-recipe.component';
import {MatStepperModule} from "@angular/material/stepper";
import {MatRadioModule} from "@angular/material/radio";
import { CreateCategoryComponent } from './create-category/create-category.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginFormComponent,
    HomepageComponent,
    RegistrationFormComponent,
    ProfileComponent,
    NavbarComponent,
    RecipeCardComponent,
    UserStatsCardComponent,
    OpinionCardComponent,
    RecipeComponent,
    RegistrationSuccessComponent,
    CategoryCardComponent,
    RecipesListComponent,
    CategoryComponent,
    CreateRecipeComponent,
    CreateCategoryComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    LayoutModule,
    MatToolbarModule,
    MatButtonModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatCardModule,
    HttpClientModule,
    MatSnackBarModule,
    MatStepperModule,
    MatRadioModule
  ],
  providers: [
    AuthService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
