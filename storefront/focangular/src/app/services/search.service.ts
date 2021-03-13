import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {SearchResult} from '../models/search-result';
import {Observable} from 'rxjs';


@Injectable()
export class SearchService {
  private _url:string = '/search?q=';

  constructor(private http: HttpClient) { }

search(searchTerm:string):Observable<SearchResult[]>{

  return this.http.get<SearchResult[]>(this._url+searchTerm, {});
}
}

