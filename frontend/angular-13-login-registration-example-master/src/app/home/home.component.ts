import { Component, OnInit } from '@angular/core';
import { tweet } from '../app.module';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  content?: string;
  tweets: tweet[] = [];

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.userService.getPublicContent().subscribe({
      next: data => {
        this.tweets = data;
      },
      error: err => {
        this.tweets = JSON.parse(err.error).message;
      }
    });
  }
}
