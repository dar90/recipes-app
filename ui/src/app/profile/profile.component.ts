import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Recipe} from "../_model/Recipe";
import {Opinion} from "../_model/Opinion";
import {UserProfile} from "../_model/UserProfile";
import {AppSettings} from "../AppSettings";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  user: UserProfile;

  latestRecipes: Recipe[];
  latestOpinions: Opinion[];

  constructor(private httpClient: HttpClient) { }

  ngOnInit(): void {
    this.httpClient.get<UserProfile>(AppSettings.API_URL + '/user/profile').subscribe(
      response => {
        this.latestRecipes = response?.recipes.sort((a, b) => b.id - a.id)
                                                .filter((recipe, index) => index < 3);
        this.latestOpinions = response?.opinions.sort((a, b) => b.id - a.id)
                                                .filter((opinion, index) => index < 3);
        this.user = response;
      }
    );
  }

}
