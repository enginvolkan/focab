import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first, tap } from 'rxjs/operators';
import { AuthenticationService } from 'src/app/authentication/authentication.service';
import { GenericResponse } from 'src/app/models/generic-response';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-password-reset',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.scss'],
})
export class ForgotPasswordComponent implements OnInit {
  passwordResetForm: FormGroup;
  loading = false;
  submitted = false;
  returnUrl: string;
  response: Observable<GenericResponse>;

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private authenticationService: AuthenticationService
  ) {
    // redirect to home if already logged in
    if (this.authenticationService.currentUserValue) {
      this.router.navigate(['/']);
    }
  }

  ngOnInit(): void {
    this.passwordResetForm = this.formBuilder.group({
      username: ['', [Validators.required,Validators.email]],
    });
    // get return url from route parameters or default to '/'
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
  }

  // convenience getter for easy access to form fields
  get f() {
    return this.passwordResetForm.controls;
  }

  onSubmit() {
    this.submitted = true;

    // stop here if form is invalid
    if (this.passwordResetForm.invalid) {
      return;
    }

    this.loading = true;

    this.response = this.authenticationService
      .generateForgotPasswordToken(this.f.username.value)
      .pipe(tap((x) => (this.loading = false)));
  }
}
