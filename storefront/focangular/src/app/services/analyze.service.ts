import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MovieSearchResult,MovieInfo,EpisodeSearchResult, MovieAnalysisResult  } from '../models/movie-model';


@Injectable()
export class MovieService {
  private _url: string = '/analyzeMovie?imdbId=';

  constructor(private http: HttpClient) { }

  analyze(imdbId: string): Observable<MovieAnalysisResult> {

    return this.http.get<MovieAnalysisResult>(this._url + imdbId, { });
  }

}

