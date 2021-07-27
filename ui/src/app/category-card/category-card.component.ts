import {Component, Input, OnInit} from '@angular/core';
import {Category} from "../_model/Category";

@Component({
  selector: 'app-category-card',
  templateUrl: './category-card.component.html',
  styleUrls: ['./category-card.component.scss']
})
export class CategoryCardComponent implements OnInit {

  @Input() category: Category;

  constructor() { }

  ngOnInit(): void {
    if(this.category.image == undefined)
      this.category.image = 'assets/img/img_placeholder.png'
  }

}
