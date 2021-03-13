import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {SearchResult} from '../models/search-result';
import {Observable} from 'rxjs';
import { LexiDetails } from '../models/lexi-details';


@Injectable()
export class LexiService {
  private _url:string = '/lexiDetails?text=';

  constructor(private http: HttpClient) { }

fetchDefinition(searchTerm:string):Observable<LexiDetails>{

  return this.http.get<LexiDetails>(this._url+searchTerm, {});
}
}

