import { Component, OnInit } from '@angular/core';
import { AppService } from '../../app.service';
import { HttpClient } from '@angular/common/http';
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  title = 'Demo';
  greeting = {};

  constructor(private appService: AppService, private http: HttpClient) {
 //   http.get('/getFavorites', {withCredentials:true}).subscribe(data => this.greeting = data);
  }

  authenticated() { return this.appService.authenticated; }

  ngOnInit() {
  }

}
