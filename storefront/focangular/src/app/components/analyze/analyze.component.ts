import { Component, OnInit, Pipe, PipeTransform } from '@angular/core';
import { Observable, of } from 'rxjs';
import { MovieAnalysisResult, MovieSearchResult } from 'src/app/models/movie-model';
import { MovieService } from 'src/app/services/analyze.service';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';

@Component({
  selector: 'app-analyze',
  templateUrl: './analyze.component.html',
  styleUrls: ['./analyze.component.css'],
  providers: [MovieService]
})
export class AnalyzeComponent implements OnInit {

  constructor(private movieService: MovieService, private route: ActivatedRoute ) { }

  imdbId:string;
  result$:Observable<MovieAnalysisResult>;
  panelOpenState = false;


  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.imdbId = params['imdbID'];
      this.result$ = this.movieService.analyze(this.imdbId);
    });
  }

  fetchDefinition(text:string):string{
    console.log(text);
    return text;
  }

}
