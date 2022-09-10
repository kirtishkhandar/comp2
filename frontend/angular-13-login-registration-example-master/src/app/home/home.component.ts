import { Component, Input, OnInit } from '@angular/core';
import { tweet, tweet1, user } from '../app.module';
import { TokenStorageService } from '../_services/token-storage.service';
import { UserService } from '../_services/user.service';
import {Router} from '@angular/router';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  showUserList: boolean = false;
  showUserButton: string = 'Show all users';
  searchTweetsByUser: string = '';
  selectedUser: any;
  content: string = 'feed';
  showUserDetail: boolean = false;
  showTweets: boolean = true;
  searchUser: string = '';
  users: user[] = [];
  tweets: tweet[] = [];
  myself: any;
  toBeTweeted: string = '';
  tweetPostSuccess: boolean = false;
  isLoggedIn: boolean = false;
  showComment: string = '';
  comment: string = '';
  showEdit: string = '';
  edit: string = '';
  @Input() defaultValue: string = '';

  constructor(private userService: UserService, private tokenStorageService: TokenStorageService, private router: Router) { }

  ngOnInit(): void {
    this.myself = this.tokenStorageService.getUser();
    this.isLoggedIn = !!this.tokenStorageService.getToken();

    this.userService.getAllUsers().subscribe({
      next: data => {
        this.users = data;
      },
      error: err => {
        this.users = JSON.parse(err.error).message;
      }
    });
    
    this.userService.getAllTweets().subscribe({
      next: data => {
        this.tweets = data;
        this.tweets.forEach(tweet => {
          console.log("tweet");
          tweet.likeByMe = false;        
          tweet.likeCount = 0;
          if (tweet.likedBy != null) {
            tweet.likeCount = tweet.likedBy.length;
            console.log("got count: "+tweet.likeCount);
          }
          tweet.replycount = 0;
          if (tweet.replies != null) {
            tweet.replycount = tweet.replies.length;
            console.log("got count: "+tweet.replycount);
          }
          if ( tweet.likedBy != null && tweet.likedBy.includes(this.myself.username)) {
            console.log("adding liked by");  
            tweet.likeByMe = true;
            console.log(tweet.likeByMe); 
          }
        });
        console.log(this.tweets);
      },
      error: err => {
        this.tweets = JSON.parse(err.error).message;
      }
    });
    
    this.tweets = this.tweets.sort((a: any, b:any) => <any>new Date(b.datetime) - <any>new Date(a.datetime));
    
  }

  onShowAllUser(){
    if (this.showUserList) {
      this.showUserList = false;
      this.showUserButton = 'Show all users';
      this.searchUser = '';
    } else {
      this.showUserList = true;
      this.showUserButton = 'Hide all users';
      this.searchUser = '';
    }
  }

  onSelectUser(user1: user){
    this.selectedUser = user1;
    this.showUserDetail = true;
    this.showTweets = false;

  }

  onShowFeed(){
    this.showTweets = true;
    this.showUserDetail = false;
    this.searchTweetsByUser = '';
    this.showComment = '';
    this.showEdit = '';
  }

  onPostTweet(toBeTweeted: string){
    this.userService.postTweet(new tweet1(this.myself.username, new Date(), toBeTweeted)).subscribe({
      next: data => {
        this.tweetPostSuccess = data;
      },
      error: err => {
        this.tweetPostSuccess = JSON.parse(err.error).message;
      }
    });
    this.toBeTweeted = '';
    setTimeout(() => {
      this.ngOnInit();
      }
      ,750);
  }

  onShowUserTweets(){
    this.showTweets = true;
    this.searchTweetsByUser = this.selectedUser.username;
  }

  onLike(id: string){
    this.userService.likeTweet(id, this.myself.username).subscribe({
      next: data => {
        this.tweetPostSuccess = data;
      },
      error: err => {
        this.tweetPostSuccess = JSON.parse(err.error).message;
      }
    });
    setTimeout(() => {
      this.ngOnInit();
      }
      ,750);
  }

  onDelete(id: string){
    this.userService.deleteTweet(id).subscribe({
      next: data => {
        this.tweetPostSuccess = data;
      },
      error: err => {
        this.tweetPostSuccess = JSON.parse(err.error).message;
      }
    });
    setTimeout(() => {
      this.ngOnInit();
      }
      ,750);
  }

  onComment(id: string){
    if (this.showComment == '') {
      this.showEdit = '';
      this.showComment = id;
    } else{
      this.showComment = '';
      this.showEdit = '';
    }
    this.ngOnInit();
  }

  onSubmitComment(id: string){   
    if (this.comment != '') {
      this.userService.replyTweet(this.myself.username, new Date(), id, this.comment).subscribe({
        next: data => {
          this.tweetPostSuccess = data;
        },
        error: err => {
          this.tweetPostSuccess = JSON.parse(err.error).message;
        }
      });
    }
    this.comment = '';
    setTimeout(() => {
      this.ngOnInit();
      }
      ,750);
  }

  onEdit(id: string){
    if (this.showEdit == '') {
      this.showComment = '';
      this.showEdit = id;
    } else{
      this.showEdit = '';
      this.showComment = '';
    }
    this.ngOnInit();
  }

  onSubmitEdit(id: string){   
    if (this.edit != '') {
      this.userService.updateTweet(id, this.myself.username, new Date(), this.edit).subscribe({
        next: data => {
          this.tweetPostSuccess = data;
        },
        error: err => {
          this.tweetPostSuccess = JSON.parse(err.error).message;
        }
      });
    }
    this.edit = '';
    this.showEdit = '';
    setTimeout(() => {
      this.ngOnInit();
      }
      ,750);
  }

}
