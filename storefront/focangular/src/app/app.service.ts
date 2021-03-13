import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpEvent } from '@angular/common/http';
import { HttpRequest, HttpInterceptor,HttpHandler } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment';



@Injectable({
  providedIn: 'root'
})
export class AppService {
  
  authenticated = false;

  constructor(private http: HttpClient) {
  }

  authenticate(credentials, callback) {

        const headers = new HttpHeaders(credentials ? {
            authorization : 'Basic ' + btoa(credentials.username + ':' + credentials.password, )
        } : {});

        this.http.get('/user', {headers: headers}).subscribe(response => {

                this.authenticated = true;
 
            return callback && callback();
        });

    }

}

@Injectable()
export class XhrInterceptor implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    const xhr = req.clone({
      headers: req.headers.set('X-Requested-With', 'XMLHttpRequest')
    });
    return next.handle(xhr);
  }
}


@Injectable()
export class BackendUrlInterceptor implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    const apiReq = req.clone({ url: environment.backendEndpoint + req.url });
    return next.handle(apiReq);
  }
}