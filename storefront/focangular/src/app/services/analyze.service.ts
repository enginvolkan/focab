import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MovieSearchResult,MovieInfo,EpisodeSearchResult, MovieAnalysisResult  } from '../models/movie-model';
import { Subtitle } from '../models/subtitle.model';


@Injectable()
export class MovieService {
  private _url: string = '/analyzeMovie?imdbId=';
  private _subtitlesUrl: string = '/getFullSubtitles?imdbId=';

  constructor(private http: HttpClient) { }

  analyze(imdbId: string): Observable<MovieAnalysisResult> {

    return this.http.get<MovieAnalysisResult>(this._url + imdbId, { });
  }

  getFullSubtitles(imdbId: string): Observable<Subtitle[]> {

    return this.http.get<Subtitle[]>(this._subtitlesUrl + imdbId, { });
  }

}

