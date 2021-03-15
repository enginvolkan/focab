import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { User } from '../models/user.model';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';


// Based on https://jasonwatmore.com/post/2019/06/26/angular-8-basic-http-authentication-tutorial-example
@Injectable({ providedIn: 'root' })
export class AuthenticationService {
    private currentUserSubject: BehaviorSubject<User>;
    public currentUser: Observable<User>;

    constructor(private http: HttpClient, private router:Router, private cookieService:CookieService) {
        this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('currentUser')));
        this.currentUser = this.currentUserSubject.asObservable();
    }

    public get currentUserValue(): User {
        return this.currentUserSubject.value;
    }

    login(username: string, password: string) {
        const headers = new HttpHeaders({
            authorization : 'Basic ' + btoa(username + ':' + password, )
        });

        return this.http.get<any>('/user', {headers: headers})
            .pipe(map(user => {
                // store user details and basic auth credentials in local storage to keep user logged in between page refreshes
                user.authdata = window.btoa(username + ':' + password);
                localStorage.setItem('currentUser', JSON.stringify(user));
                this.currentUserSubject.next(user);
                return user;
            }));
    }

    logout() {
        // remove user from local storage to log user out
        this.http.post<any>('/logout',"",{}).subscribe(
            ()=>{
            localStorage.removeItem('currentUser');
            this.currentUserSubject.next(null);
            this.router.navigateByUrl('/login');
        // Cookies will be removed by the backend response    
            }
        );



    }
}