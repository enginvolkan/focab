import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable} from 'rxjs';


@Injectable()
export class FavoriteService {
  private _addFavoriteUrl:string = '/addFavorite?entry=';
  private _removeFavoriteUrl:string = '/removeFavorite';

  constructor(private http: HttpClient) { }

addFavorite(word:string):Observable<boolean>{
  //return this.http.post<boolean>(this._addFavoriteUrl,"\"entry\":\""+word+"\"");

  return this.http.post<boolean>(this._addFavoriteUrl+word,"",{});
}
removeFavorite(word:string):Observable<boolean>{
  return this.http.post<boolean>(this._removeFavoriteUrl,"entry:"+word, {});
}
}

