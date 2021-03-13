import { Component, OnInit , Input} from '@angular/core';
import {SearchResult} from '../../models/search-result';
import {FavoriteService} from '../../services/favorite.service';
import {Router, RouterEvent, NavigationEnd} from '@angular/router';
import { filter } from 'rxjs/operators';
import { AlertService } from 'src/app/services/alert.service';


@Component({
  selector: 'app-search-result',
  templateUrl: './search-result.component.html',
  styleUrls: ['./search-result.component.css']
})
export class SearchResultComponent implements OnInit {

searchResult: SearchResult[];

result:boolean;

  constructor(private favoriteService:FavoriteService,private router: Router,private alertService:AlertService) { 
    this.searchResult=this.router.getCurrentNavigation().extras.state.searchResults;

  }

  ngOnInit() {
    this.router.events.pipe(
      filter((event: RouterEvent) => event instanceof NavigationEnd)
    ).subscribe(() => {
      this.searchResult=this.router.getCurrentNavigation().extras.state.searchResults;

    });


  }

  addFavorite(word:string){
    console.log("Add to favorite: "+word);
    this.result=null;
    const searchResultObservable = this.favoriteService.addFavorite(word);
    searchResultObservable.subscribe((flag:boolean) => 
      {
        this.result=flag;
        this.alertService.success("Favorite added to the list");
        console.log("Add to favorite result: "+this.result);
      }
      );
  }

}