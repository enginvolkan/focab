import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first, take, tap } from 'rxjs/operators';
import { AuthenticationService } from 'src/app/authentication/authentication.service';
import { GenericResponse } from 'src/app/models/generic-response';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.scss'],
})
export class ResetPasswordComponent implements OnInit {
  resetPasswordForm: FormGroup;
  loading = false;
  submitted = false;
  returnUrl: string;
  response: Observable<GenericResponse>;
  token: string;
  tokenValidityResponse: Observable<GenericResponse>;

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
    this.resetPasswordForm = this.formBuilder.group(
      {
        password1: [
          '',
          [
            Validators.required,
            Validators.minLength(8),
            Validators.pattern(
              '^(?=.*[A-Za-z.,@$!%*#?&])(?=.*\\d)[A-Za-z\\d.,@$!%*#?&]{8,}$'
            ),
          ],
        ],
        password2: ['', [Validators.required]],
      },
      { validators: this.checkPasswords }
    );
    // get return url from route parameters or default to '/'
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
    this.token = this.route.snapshot.paramMap.get('token');
    console.log(this.token);
    this.tokenValidityResponse =
      this.authenticationService.checkForgotPasswordTokenValidity(this.token);
  }

  // convenience getter for easy access to form fields
  get f() {
    return this.resetPasswordForm.controls;
  }

  onSubmit() {
    this.submitted = true;

    // stop here if form is invalid
    if (this.resetPasswordForm.invalid) {
      return;
    }

    this.loading = true;

    this.response = this.authenticationService
      .resetPassword(this.f.password1.value, this.token)
      .pipe(
        tap((x) => {
          this.loading = false;
          this.resetPasswordForm.reset();
        })
      );
  }

  checkPasswords(group: FormGroup) {
    // here we have the 'passwords' group
    const password = group.get('password1').value;
    const confirmPassword = group.get('password2').value;

    return password === confirmPassword ? null : { notSame: true };
  }
}
