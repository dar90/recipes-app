import { Component, OnInit } from '@angular/core';
import {Recipe} from "../_model/Recipe";
import {HttpClient} from "@angular/common/http";
import {AppSettings} from "../AppSettings";

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.scss']
})
export class HomepageComponent implements OnInit {

  latestRecipes: Recipe[];

  constructor(private httpClient: HttpClient) { }

  ngOnInit(): void {
    this.httpClient.get<Recipe[]>(AppSettings.API_URL + '/recipe').subscribe(
      response => {
        this.latestRecipes = response.sort((a, b) => b.id - a.id)
                                      .filter((recipe, index) => index < 8)
      }
    );
  }

}
