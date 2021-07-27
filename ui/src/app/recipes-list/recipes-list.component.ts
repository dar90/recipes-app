import {Component, Input, OnInit} from '@angular/core';
import {Recipe} from "../_model/Recipe";

@Component({
  selector: 'app-recipes-list',
  templateUrl: './recipes-list.component.html',
  styleUrls: ['./recipes-list.component.scss']
})
export class RecipesListComponent implements OnInit {

  @Input() recipesList: Recipe[];

  constructor() { }

  ngOnInit(): void {
  }

}
