import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { SearchService } from '../../services/search.service';
import { SearchResult } from '../../models/search-result';
import { Router } from '@angular/router';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  searchTerm: string;
  rawSearchResult: SearchResult[];
  exactMatch: boolean = false;
  @Output() searchResult = new EventEmitter<SearchResult[]>();

  constructor(private searchService: SearchService,private router : Router) { }

  ngOnInit() {
  }

  triggerSearch(searchTerm) {
    const searchResultObservable = this.searchService.search(this.searchTerm);
    searchResultObservable.subscribe((data: SearchResult[]) => {
      var finalSearchResult  : SearchResult[] = [];
      this.rawSearchResult = data;
      this.exactMatch=false;

      // **********************
      // If exact search phrase is not among the search searchResultObservable, 
      // insert an additional search result at the beginning just for adding the phrase to a favorite list
      // that is the exact match scenario
      // **********************

      this.rawSearchResult.forEach(element => {

        if (Object.assign(new SearchResult(element.word, element.score), element).equals(searchTerm)) {
          this.exactMatch = true;
          return;
        }
      });
      if (!this.exactMatch) {
        finalSearchResult.push(new SearchResult(searchTerm, -999));
        this.rawSearchResult.forEach(element => {
        finalSearchResult.push(element);

        });
      } else {
        finalSearchResult = this.rawSearchResult;
      }

      // *******************
      // this is how we send search results to the router OutletContext
      // state is a router variable
      // *******************
    this.router.navigate(['/searchResults'],{state:{searchResults:finalSearchResult}});

    }
    );
  }

}