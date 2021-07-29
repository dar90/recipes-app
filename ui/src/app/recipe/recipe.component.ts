import {Component, OnInit} from '@angular/core';
import {Recipe} from "../_model/Recipe";
import {AppSettings} from "../AppSettings";
import {HttpClient} from "@angular/common/http";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-recipe',
  templateUrl: './recipe.component.html',
  styleUrls: ['./recipe.component.scss']
})
export class RecipeComponent implements OnInit {

  recipe: Recipe;

  constructor(private httpClient: HttpClient,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');

    this.httpClient.get<Recipe>(AppSettings.API_URL + '/recipe/' + id).subscribe({
      next: (response) => {
        if(response != null) {
          this.recipe = response;
          if (this.recipe.image == null) {
            this.recipe.image = 'assets/img/img_placeholder.png'
    }}}}
    );
  }

  get avgGrade(): number {
    if(this.recipe.opinions.length === 0)
      return 0.0;

    return this.recipe.opinions.map<number>(o => {
      switch (o.grade) {
        case 'FIVE_STARS':
          return 5.0;
        case 'FOUR_STARS':
          return 4.0;
        case 'THREE_STARS':
          return 3.0;
        case 'TWO_STARS':
          return 2.0;
        case 'ONE-STAR':
          return 1.0;
        default:
          return 0.0;
      }
    }).reduce((avg, current) => avg + current) / (this.recipe.opinions.length);
  }

  get difficulty(): string {
    switch (this.recipe.difficulty) {
      case 'HARD':
        return 'TRUDNY';
      case 'MEDIUM':
        return 'ŚREDNI';
      case 'EASY':
        return 'ŁATWY';
      default:
        return ''
    }
  }
}
