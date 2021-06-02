import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';
import { AuthenticationService } from 'src/app/authentication/authentication.service';
import { User } from 'src/app/models/user.model';

interface Level {
  id: number;
  text: string;
}

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  profileForm: FormGroup;
  loading = false;
  submitted = false;
  returnUrl: string;
  error = '';
  selectedLevel:number;

  localuser:User;

  levels: Level[] = [
    {id: 0, text: 'Beginner'},
    {id: 1, text: 'Elementary'},
    {id: 2, text: 'Intermediate'},
    {id: 3, text: 'Upper Intermediate'},
    {id: 4, text: 'Advanced'}
  ];

  constructor(
      private formBuilder: FormBuilder,
      private route: ActivatedRoute,
      private router: Router,
      private authenticationService: AuthenticationService
  ) { 
      // redirect to home if not logged in
      if (!this.authenticationService.currentUserValue) { 
          this.router.navigate(['/']);
      }
  }

  ngOnInit() {
    this.localuser = JSON.parse(localStorage.getItem("currentUser"));
      this.profileForm = this.formBuilder.group({
          fullname: [this.localuser.fullname, Validators.required],
          level: [this.localuser.level, Validators.required]
      });

      // get return url from route parameters or default to '/'
      this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
  }

  // convenience getter for easy access to form fields
  get f() { return this.profileForm.controls; }

  onSubmit() {
      this.submitted = true;

      // stop here if form is invalid
      if (this.profileForm.invalid) {
          return;
      }

      this.loading = true;
      this.authenticationService.updateProfile(this.f.fullname.value, this.f.level.value).subscribe(
        user=> {this.loading =false;}
      );
  }

}
