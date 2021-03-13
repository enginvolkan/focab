import { Component, OnInit } from '@angular/core';
import { IdiomService } from 'src/app/services/idiom.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-idiom-detector',
  templateUrl: './idiom-detector.component.html',
  styleUrls: ['./idiom-detector.component.css']
})
export class IdiomDetectorComponent implements OnInit {
  sentence: string;
  idiomSearchResult: String[];

  constructor(private idiomService: IdiomService,private router : Router) { }

  ngOnInit() {
  }


  triggerSearch(searchTerm) {
    const searchResultObservable = this.idiomService.findIdioms(this.sentence);
    searchResultObservable.subscribe((data: String[]) => {
      this.idiomSearchResult = data;
    }
    );
  }



}
