import {Component, OnInit} from '@angular/core';
import {Category} from "../_model/Category";
import {HttpClient} from "@angular/common/http";
import {AppSettings} from "../AppSettings";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.scss']
})
export class CategoryComponent implements OnInit {

  category: Category;

  constructor(private httpClient: HttpClient,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.params.subscribe(
      params => {
        this.httpClient.get<Category>(AppSettings.API_URL + '/category/' + params['name']).subscribe(
          response => this.category = response
        )
      }
    );
  }

  get recipes() {
    return this.category?.recipes;
  }

}
