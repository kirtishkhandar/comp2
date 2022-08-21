import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tweet, tweet1, user } from '../app.module';

const API_URL = 'http://localhost:8080/api/test/';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private http: HttpClient) { }

  getAllTweets(): Observable<tweet[]> {
    return this.http.get<tweet[]>(API_URL + 'all');
  }

  getAllUsers(): Observable<user[]> {
    return this.http.get<user[]>(API_URL + 'users/all');
  }

  getUserBoard(): Observable<any> {
    return this.http.get(API_URL + 'user', { responseType: 'text' });
  }

  getModeratorBoard(): Observable<any> {
    return this.http.get(API_URL + 'mod', { responseType: 'text' });
  }

  getAdminBoard(): Observable<any> {
    return this.http.get(API_URL + 'admin', { responseType: 'text' });
  }

  postTweet(tweet: tweet1): Observable<any> {
    return this.http.post(API_URL + tweet.username +'/add', tweet);
  }

  likeTweet(id: string, username: string): Observable<any> {
    return this.http.put(API_URL +'like/'+ id , {"username" : username});
  }

  deleteTweet(id: string): Observable<any> {
    return this.http.delete(API_URL +'delete/'+ id);
  }

  replyTweet(repliedby: string, date: Date, tweetId: string, reply:string): Observable<any> {
    return this.http.post(API_URL +'reply/'+ tweetId, {"repliedby" : repliedby, "LocalDateTime" : date, "tweetId" : tweetId, "reply" : reply});
  }

  updateTweet(tweetId: string, username: string, date: Date, updatedTweet: string): Observable<any> {
    return this.http.put(API_URL +'update/'+ tweetId, {"username" : username, "datetime" : date, "body" : updatedTweet});
  }
}
