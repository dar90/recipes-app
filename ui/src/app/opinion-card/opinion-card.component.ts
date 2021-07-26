import {Component, Input, OnInit} from '@angular/core';
import {Opinion} from "../_model/Opinion";

@Component({
  selector: 'app-opinion-card',
  templateUrl: './opinion-card.component.html',
  styleUrls: ['./opinion-card.component.scss']
})
export class OpinionCardComponent implements OnInit {

  @Input() opinion: Opinion;

  constructor() { }

  ngOnInit(): void {
  }

}
