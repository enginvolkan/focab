import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthenticationService } from '../authentication/authentication.service';

@Injectable({
  providedIn: 'root'
})
export class KnownWordsListService {

  private _addKnownWordUrl:string = '/addKnownWord?entry=';
  private _removeKnownWordUrl:string = '/removeKnownWord?entry=';
  private _getKnownWordsUrl:string = '/getKnownWords';

  constructor(private http: HttpClient, private authenticationService:AuthenticationService) { }

addKnownWord(word:string):Observable<boolean>{
  //return this.http.post<boolean>(this._addFavoriteUrl,"\"entry\":\""+word+"\"");

  return this.http.post<boolean>(this._addKnownWordUrl+word,"",{});
}
removeKnownWord(word:string):Observable<boolean>{
  return this.http.post<boolean>(this._removeKnownWordUrl+word,"", {});
}

getKnownWords():Observable<String[]>{
  return this.http.get<String[]>(this._getKnownWordsUrl,{});
}
}
