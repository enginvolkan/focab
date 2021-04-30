import { Component, OnInit, Pipe, PipeTransform } from '@angular/core';
import { Observable, of } from 'rxjs';
import {
  Lexi,
  MovieAnalysisResult,
  MovieSearchResult,
} from 'src/app/models/movie-model';
import { MovieService } from 'src/app/services/analyze.service';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { map, mergeMap, filter, tap } from 'rxjs/operators';
import { SearchService } from 'src/app/services/search.service';
import { FavoriteService } from 'src/app/services/favorite.service';
import { AlertService } from 'src/app/services/alert.service';
import { BugService } from 'src/app/services/bug.service';
import { KnownWordsListService } from 'src/app/services/known-words-list.service';
import { OverlayRef } from '@angular/cdk/overlay';
import { OverlayService } from 'src/app/services/overlay.service';
import { SpinnerComponent } from '../spinner/spinner.component';

@Component({
  selector: 'app-analyze',
  templateUrl: './analyze.component.html',
  styleUrls: ['./analyze.component.css'],
  providers: [MovieService],
})
export class AnalyzeComponent implements OnInit {
  constructor(
    private movieService: MovieService,
    private route: ActivatedRoute,
    private searchService: SearchService,
    private favoriteService: FavoriteService,
    private alertService: AlertService,
    private bugService: BugService,
    private knownWordService: KnownWordsListService,
    private overlayService:OverlayService
  ) {}

  imdbId: string;
  result$: Observable<MovieAnalysisResult>;
  idioms: Observable<Lexi[]>;
  phrasalVerbs: Observable<Lexi[]>;
  singleWords: Observable<Lexi[]>;

  idiomDefinitions$: Observable<string[]>[] = [];
  phrasalDefinitions$: Observable<string[]>[] = [];
  singleWordDefinitions$: Observable<string[]>[] = [];

  ngOnInit() {
    const spinner:OverlayRef=this.overlayService.open({ hasBackdrop: true },SpinnerComponent);

    this.route.queryParams.subscribe((params) => {
      this.imdbId = params['imdbID'];
      this.result$ = this.movieService.analyze(this.imdbId).pipe(tap(x=>this.overlayService.close(spinner)));
      this.idioms = this.result$.pipe(map((x) => x.idioms));
      this.phrasalVerbs = this.result$.pipe(map((x) => x.phrasalVerbs));
      this.singleWords = this.result$.pipe(map((x) => x.singleWords));
    });
  }

  fetchIdiomDefinition(text: string, index: number) {
    if (!this.idiomDefinitions$[index]) {
      console.log(text);
      this.idiomDefinitions$[index] = this.searchService
        .search(text)
        .pipe(
          map((searchResultList) =>
            searchResultList
              .filter(
                (searchResult) =>
                  searchResult.word.toUpperCase() === text.toUpperCase()
              )
              .map((searchResult) =>
                searchResult.defs.map((def) => def.definition)
              )
              .reduce((acc, val) => acc.concat(val), [])
          )
        );
    }
  }

  fetchPhrasalDefinition(text: string, index: number) {
    if (!this.phrasalDefinitions$[index]) {
      console.log(text);
      this.phrasalDefinitions$[index] = this.searchService
        .search(text)
        .pipe(
          map((searchResultList) =>
            searchResultList
              .filter(
                (searchResult) =>
                  searchResult.word.toUpperCase() === text.toUpperCase()
              )
              .map((searchResult) =>
                searchResult.defs.map((def) => def.definition)
              )
              .reduce((acc, val) => acc.concat(val), [])
          )
        );
    }
  }
  fetchSingleWordDefinition(text: string, index: number) {
    if (!this.singleWordDefinitions$[index]) {
      console.log(text);
      this.singleWordDefinitions$[index] = this.searchService
        .search(text)
        .pipe(
          map((searchResultList) =>
            searchResultList
              .filter(
                (searchResult) =>
                  searchResult.word.toUpperCase() === text.toUpperCase()
              )
              .map((searchResult) =>
                searchResult.defs.map((def) => def.tag.concat(" - ", def.definition))
              )
              .reduce((acc, val) => acc.concat(val), [])
          )
        );
    }
  }
  addToFavorites(text: string) {
    console.log('Add to favorite: ' + text);
    const searchResultObservable = this.favoriteService.addFavorite(text);
    searchResultObservable.subscribe((flag: boolean) => {
      if(flag){
      this.alertService.success('Favorite added to the list');
      console.log('Add to favorite result: ' + flag);
      }else{
        this.alertService.warn('Already in the favorites');
      }
    });
  }

  checkFavoriteStatus(text: string): boolean {
    return false;
  }

  addToKnownWords(text: string) {
    console.log('Add to known words: ' + text);
    const searchResultObservable = this.knownWordService.addKnownWord(text);
    searchResultObservable.subscribe((flag: boolean) => {
      if(flag){
      this.alertService.success('Known word added to the list');
      }else{
        this.alertService.warn('Already in the known words');
      }
    });
  }

  addToBug(item: Lexi, type: string) {
    console.log('Add to bugs: ' + item.text);
    const searchResultObservable = this.bugService.addBug(item, type);
    searchResultObservable.subscribe((flag: boolean) => {
      this.alertService.success('Bug reported!');
      console.log('Report bug result: ' + flag);
    });
  }
}
