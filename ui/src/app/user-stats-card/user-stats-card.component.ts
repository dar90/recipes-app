import {Component, Input, OnInit} from '@angular/core';
import {UserProfile} from "../_model/UserProfile";

@Component({
  selector: 'app-user-stats-card',
  templateUrl: './user-stats-card.component.html',
  styleUrls: ['./user-stats-card.component.scss']
})
export class UserStatsCardComponent implements OnInit {

  @Input() user: UserProfile;

  constructor() { }

  ngOnInit(): void {
  }

}
