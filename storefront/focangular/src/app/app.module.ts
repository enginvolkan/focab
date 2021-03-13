import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AppService } from './app.service';
import { SearchService } from './services/search.service';
import { SearchComponent } from './components/search/search.component';
import { SearchResultComponent } from './components/search-result/search-result.component';
import { FavoriteService } from './services/favorite.service';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { HomeComponent } from './components/home/home.component';
import { XhrInterceptor } from './app.service';
import { HTTP_INTERCEPTORS} from '@angular/common/http';
import {BackendUrlInterceptor} from './app.service';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { FocabMaterialModule } from './ui/material.module';
import { FavoritesListComponent } from './components/favorites-list/favorites-list.component';
import { LearnComponent } from './components/learn/learn.component';
import { FlexLayoutModule } from '@angular/flex-layout';
import { ServiceWorkerModule } from '@angular/service-worker';
import { environment } from '../environments/environment';
import { SpinnerComponent } from './components/spinner/spinner.component';
import { AlertComponent } from './components/alert/alert.component';
import { AlertModule } from './components/alert/alert.module';
import { MovieSearchComponent } from './components/movie/movie-search/movie-search.component';
import { MovieService } from './services/movie.service';
import { MovieEpisodeDetailComponent } from './components/movie/movie-episode-detail/movie-episode-detail.component';
import { AnalyzeComponent } from './components/analyze/analyze.component';
import { IdiomDetectorComponent } from './components/idiom-detector/idiom-detector.component';
import { IdiomService } from './services/idiom.service';
import { LexiService } from './services/lexi.service';
import { PipeModule } from './pipe/pipe.module';
import { BasicAuthInterceptor } from './authentication/basic-auth.interceptor';
import { ErrorInterceptor } from './authentication/error.interceptor';

const routes: Routes = [
  // { path: '', pathMatch: 'full', redirectTo: 'home'},
  { path: 'home', component: HomeComponent},
  { path: 'login', component: LoginComponent},
  { path: 'learn', component: LearnComponent},
  { path: 'searchResults', component: SearchResultComponent},
  { path: 'movieSearch', component: MovieSearchComponent},
  { path: 'idiomSearch', component: IdiomDetectorComponent},
  { path: 'analyze', component: AnalyzeComponent}
];


@NgModule({
  declarations: [
    AppComponent, SearchComponent, SearchResultComponent, HomeComponent, LoginComponent, HeaderComponent, FooterComponent, FavoritesListComponent, LearnComponent, SpinnerComponent, MovieSearchComponent, MovieEpisodeDetailComponent, AnalyzeComponent, IdiomDetectorComponent
  ],
  entryComponents: [SpinnerComponent,MovieEpisodeDetailComponent],

  imports: [
    RouterModule.forRoot(routes, {
      onSameUrlNavigation: 'reload'
    }),
    BrowserModule,
    AppRoutingModule,
    FormsModule , HttpClientModule, BrowserAnimationsModule, FocabMaterialModule,FlexLayoutModule, ServiceWorkerModule.register('ngsw-worker.js', { enabled: environment.production }),AlertModule,PipeModule,ReactiveFormsModule
  ],
  providers: [AppService, 
            { provide: HTTP_INTERCEPTORS, useClass: XhrInterceptor, multi: true },
            { provide: HTTP_INTERCEPTORS, useClass: BackendUrlInterceptor, multi: true},
            { provide: HTTP_INTERCEPTORS, useClass: BasicAuthInterceptor, multi: true },
            { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
            SearchService,
            MovieService,
            IdiomService,
            FavoriteService,
          MovieService,
        LexiService],
  bootstrap: [AppComponent]
})
export class AppModule { }
