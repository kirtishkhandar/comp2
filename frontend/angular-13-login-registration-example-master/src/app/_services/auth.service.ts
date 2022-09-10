import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

const AUTH_API = 'http://18.213.247.39:8080/api/auth/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient) { }

  login(username: string, password: string): Observable<any> {
    return this.http.post(AUTH_API + 'signin', {
      username,
      password
    }, httpOptions);
  }

  register(firstName: string, lastName: string, username: string, email: string, password: string, mobile: string): Observable<any> {
    return this.http.post(AUTH_API + 'signup', {
      firstName,
      lastName,
      username,
      email,
      password,
      mobile
    }, httpOptions);
  }
}
