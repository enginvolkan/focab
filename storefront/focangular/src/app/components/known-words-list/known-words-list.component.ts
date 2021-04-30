import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { AlertService } from 'src/app/services/alert.service';
import { KnownWordsListService } from 'src/app/services/known-words-list.service';

@Component({
  selector: 'app-known-words-list',
  templateUrl: './known-words-list.component.html',
  styleUrls: ['./known-words-list.component.scss']
})
export class KnownWordsListComponent implements OnInit {

  constructor(private knownWordService:KnownWordsListService,private alertService:AlertService) { }

  knownWords$:Observable<String[]>=this.knownWordService.getKnownWords();

  ngOnInit(): void {
  }

  removeKnownWord(text: string) {
    console.log('Remove known word: ' + text);
    const searchResultObservable = this.knownWordService.removeKnownWord(text);
    searchResultObservable.subscribe((flag: boolean) => {
      if(flag){
      this.alertService.success('Known word removed');
      }else{
        this.alertService.warn('Known word  could not be removed');
      }
    });
  }
}
