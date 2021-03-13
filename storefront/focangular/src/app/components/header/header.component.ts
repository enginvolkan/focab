import { Component, OnInit} from '@angular/core';
import { AuthenticationService } from 'src/app/authentication/authentication.service';

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
}
