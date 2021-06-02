import { Component, OnInit} from '@angular/core';
import { Observable } from 'rxjs';
import { AuthenticationService } from 'src/app/authentication/authentication.service';
import { User } from 'src/app/models/user.model';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(
    private authenticationService:AuthenticationService) {
   }

  authenticated() { 
    return this.authenticationService.currentUserValue;
  }

  logout() {
    return this.authenticationService.logout();
  }
  
  ngOnInit() {
  }

  getUser():Observable<User>{
    return this.authenticationService.currentUser;
  }
}
