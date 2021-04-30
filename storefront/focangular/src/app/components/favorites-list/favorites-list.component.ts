import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { AlertService } from 'src/app/services/alert.service';
import { FavoriteService } from 'src/app/services/favorite.service';

@Component({
  selector: 'app-favorites-list',
  templateUrl: './favorites-list.component.html',
  styleUrls: ['./favorites-list.component.css']
})
export class FavoritesListComponent implements OnInit {

  constructor(private favoriteService:FavoriteService,private alertService:AlertService) { }

  favorites$:Observable<String[]>=this.favoriteService.getFavorites();
  ngOnInit() {
  }

  removeFavorite(text: string) {
    console.log('Remove favorite: ' + text);
    const searchResultObservable = this.favoriteService.removeFavorite(text);
    searchResultObservable.subscribe((flag: boolean) => {
      if(flag){
      this.alertService.success('Favorite removed');
      }else{
        this.alertService.warn('Favorite could not be removed');
      }
    });
  }

}
