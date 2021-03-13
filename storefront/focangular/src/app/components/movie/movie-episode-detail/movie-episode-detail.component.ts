import { Component, OnInit, Inject, ViewChild } from '@angular/core';
import { MovieService } from 'src/app/services/movie.service';
import { MovieInfo, EpisodeSearchResult } from 'src/app/models/movie-model';

import { Router } from '@angular/router';
import { MatSelectionList, MatSelectionListChange } from '@angular/material/list';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-movie-episode-detail',
  templateUrl: './movie-episode-detail.component.html',
  styleUrls: ['./movie-episode-detail.component.css']
})
export class MovieEpisodeDetailComponent implements OnInit {
  public movieEpisodeDetail:MovieInfo= new MovieInfo();
  private episodes:EpisodeSearchResult;
  private imdbID:string;
  private seasons:number[];
  private selectedEpisode:number=1;

  @ViewChild(MatSelectionList, {static: true})
  private selectionList: MatSelectionList;


  constructor(private movieService:MovieService,  @Inject(MAT_DIALOG_DATA) public data: any, private router: Router,public dialogRef: MatDialogRef<MovieEpisodeDetailComponent>
  ) { }

  ngOnInit() {
    this.imdbID = this.data.imdbID;
    this.getDetails();
  }

  private getDetails():void {
    const resultObservable = this.movieService.getDetails(this.imdbID);
    resultObservable.subscribe((data: MovieInfo) => {
      this.movieEpisodeDetail = data;
      if(this.movieEpisodeDetail.type=='series'){
        const resultObservable2 = this.movieService.getEpisodes(this.imdbID,this.selectedEpisode);
        resultObservable2.subscribe((data: EpisodeSearchResult) => {
          this.episodes = data;
          let list = Array<number>(+this.episodes.totalSeasons);
          for(let i=0;i<+this.episodes.totalSeasons;i++){
            list[i]=i+1;
          }
          this.seasons = list;
        });
      }
    }
    );
  }


  private onSeasonSelected(){
    // let value = event.target.value;
    const resultObservable2 = this.movieService.getEpisodes(this.imdbID,this.selectedEpisode);
    resultObservable2.subscribe((data: EpisodeSearchResult) => {
      this.episodes = data;
    });
  }

  private onEpisodeSelected(){
    // let value = event.target.value;
    this.selectionList.selectionChange.subscribe((s: MatSelectionListChange) => {          

      this.selectionList.deselectAll();
      s.option.selected = true;
  });
  }

  private analyzeMovieOrEpisode(imdbID:string){
    this.dialogRef.close();
    this.router.navigateByUrl('/analyze?imdbID='+imdbID);
  }
}
