import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {Observable} from 'rxjs';
import { AuthenticationService } from '../authentication/authentication.service';
import { Lexi } from '../models/movie-model';


@Injectable()
export class BugService {
  private _addBugUrl:string = '/addBug?entry=';

  constructor(private http: HttpClient) { }

addBug(item:Lexi,type:string):Observable<boolean>{
  let body:String="";
  const headers = new HttpHeaders({
    'Content-Type': 'application/json' 
});

  body=body.concat("{\"text\":\"",item.text,"\",\"type\":\"",type,"\",\"sentences\":[");
  item.movieExamples.forEach(sentence=> body=body.concat("\"",escape(sentence),"\","));
  body=body.substr(0,body.length-1).concat("]}");
  return this.http.post<boolean>(this._addBugUrl,body,{headers: headers});
}
}

