import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { HomeComponent } from './home/home.component';
import { ProfileComponent } from './profile/profile.component';
import { BoardAdminComponent } from './board-admin/board-admin.component';
import { BoardModeratorComponent } from './board-moderator/board-moderator.component';
import { BoardUserComponent } from './board-user/board-user.component';

import { authInterceptorProviders } from './_helpers/auth.interceptor';
import { SearchFilterPipe } from './_helpers/search-filter';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    ProfileComponent,
    BoardAdminComponent,
    BoardModeratorComponent,
    BoardUserComponent,
    SearchFilterPipe
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [authInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }

export class tweet {
  constructor(
    public id: string,
    public username: string,
    public datetime: Date,
    public body: string,
    public likedBy: string[],
    public replies: reply[],
    public likeCount: number,
    public likeByMe: boolean
  ){
  }
}

export class tweet1 {
  constructor(
    public username: string,
    public datetime: Date,
    public body: string,
  ){
  }
}

export class reply {
  constructor(
    public id: string,
    public repliedby: string,
    public datetime: Date,
    public tweetId: string,
    public reply: string
  ){
  }
}

export class user {
  constructor(
    public firstName: string,
    public lastName: string,
    public username: Date,
    public email: string,
    public mobile: string
  ){
  }
}