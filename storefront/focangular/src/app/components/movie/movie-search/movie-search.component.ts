import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { MovieService } from '../../../services/movie.service';
import { MovieSearchResult,MovieDetails} from '../../../models/movie-model';
import { Router } from '@angular/router';
import { AlertService } from 'src/app/services/alert.service';

import { MovieEpisodeDetailComponent } from '../movie-episode-detail/movie-episode-detail.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-movie-search',
  templateUrl: './movie-search.component.html',
  styleUrls: ['./movie-search.component.css']
})
export class MovieSearchComponent implements OnInit {

  searchTerm: string;
  movieSearchResult: MovieSearchResult;
  movieDetails:MovieDetails[];
  selectedEpisode:string;

  constructor(private movieService: MovieService,private router : Router, private alertService:AlertService,public dialog: MatDialog) { }

  ngOnInit() {
  }

  triggerSearch(searchTerm) {
    const searchResultObservable = this.movieService.search(this.searchTerm);
    searchResultObservable.subscribe((data: MovieSearchResult) => {
      this.movieSearchResult = data;

      if(this.movieSearchResult.totalResults > 0 ){
        this.movieDetails = this.movieSearchResult.search;
        this.alertService.success(this.movieSearchResult.totalResults + " Movie or TV Series Found");
      }else{
        this.alertService.warn("No results found");
      }
    }
    );
  }

  getDetails(imdbID:string):void {

    const dialogRef = this.dialog.open(MovieEpisodeDetailComponent, {
      width: '%85',
      data: {imdbID: imdbID}
    });

  }
}