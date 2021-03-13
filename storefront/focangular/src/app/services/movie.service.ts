import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MovieSearchResult,MovieInfo,EpisodeSearchResult  } from '../models/movie-model';


@Injectable()
export class MovieService {
  private _searchUrl: string = '/movieInfo/findMovieOrSeries?searchText=';
  private _detailUrl: string = '/movieInfo/getMovieOrSeriesDetails?imdbId=';
  private _episodeUrl: string = '/movieInfo/getSeriesEpisodes?imdbId=';

  constructor(private http: HttpClient) { }

  search(searchTerm: string): Observable<MovieSearchResult> {

    return this.http.get<MovieSearchResult>(this._searchUrl + searchTerm, { });
  }

  getDetails(imdbID: string): Observable<MovieInfo> {

    return this.http.get<MovieInfo>(this._detailUrl + imdbID, { });
  }
  getEpisodes(imdbID: string, season: number): Observable<EpisodeSearchResult> {

    return this.http.get<EpisodeSearchResult>(this._episodeUrl + imdbID + "&season=" + season, {  });
  }
}

