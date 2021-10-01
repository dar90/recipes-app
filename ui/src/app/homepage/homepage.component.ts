import { Component, OnInit } from '@angular/core';
import {Recipe} from "../_model/Recipe";
import {HttpClient} from "@angular/common/http";
import {AppSettings} from "../AppSettings";
import {Category} from "../_model/Category";
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.scss']
})
export class HomepageComponent implements OnInit {

  latestRecipes: Recipe[];
  categories: Category[];

  constructor(private httpClient: HttpClient, 
              private snackBar: MatSnackBar) { }

  ngOnInit(): void {
    if(!sessionStorage.getItem('showHostingAndAccountInfo')) {
      this.snackBar.open('Ładowanie danych może zająć kilkanaście sekund, ponieważ strona korzysta z darmowego hostingu - prosimy o chwilę cierpliwości :)', 'OK', {duration: 10000});
      this.snackBar._openedSnackBarRef?.afterDismissed().subscribe(
        () => this.snackBar.open('Konto demonstracyjne - login: admin, hasło: admin', 'OK')
      );
    }
    sessionStorage.setItem('showHostingAndAccountInfo', 'false');

    this.httpClient.get<Recipe[]>(AppSettings.API_URL + '/recipe').subscribe(
      response => {
        this.latestRecipes = response.sort((a, b) => b.id - a.id)
                                      .filter((recipe, index) => index < 8)
      }
    );

    this.httpClient.get<Category[]>(AppSettings.API_URL + '/category').subscribe(
      response => this.categories = response.filter(category => (category?.recipes ?? []).length > 0)
    );
  }

}
