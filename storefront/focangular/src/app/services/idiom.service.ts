import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MovieSearchResult,MovieInfo,EpisodeSearchResult  } from '../models/movie-model';


@Injectable()
export class IdiomService {
  private _url: string = '/detectIdiom?sentence=';

  constructor(private http: HttpClient) { }

  findIdioms(sentence: string): Observable<String[]> {

    return this.http.get<String[]>(this._url + sentence, { });
  }

}

