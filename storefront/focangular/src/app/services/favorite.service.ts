import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable} from 'rxjs';
import { AuthenticationService } from '../authentication/authentication.service';


@Injectable()
export class FavoriteService {
  private _addFavoriteUrl:string = '/addFavorite?entry=';
  private _removeFavoriteUrl:string = '/removeFavorite?entry=';
  private _getFavoritesUrl:string = '/getFavorites';

  constructor(private http: HttpClient, private authenticationService:AuthenticationService) { }

addFavorite(word:string):Observable<boolean>{
  //return this.http.post<boolean>(this._addFavoriteUrl,"\"entry\":\""+word+"\"");

  return this.http.post<boolean>(this._addFavoriteUrl+word,"",{});
}
removeFavorite(word:string):Observable<boolean>{
  return this.http.post<boolean>(this._removeFavoriteUrl+word,"", {});
}

getFavorites():Observable<String[]>{
  return this.http.get<String[]>(this._getFavoritesUrl,{});
}
}

