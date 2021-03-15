import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from './authentication/auth.guard';
import { AnalyzeComponent } from './components/analyze/analyze.component';
import { HomeComponent } from './components/home/home.component';
import { IdiomDetectorComponent } from './components/idiom-detector/idiom-detector.component';
import { LearnComponent } from './components/learn/learn.component';
import { LoginComponent } from './components/login/login.component';
import { MovieSearchComponent } from './components/movie/movie-search/movie-search.component';
import { SearchResultComponent } from './components/search-result/search-result.component';

const routes: Routes = [
  // { path: '', pathMatch: 'full', redirectTo: 'home'},
  { path: 'home', component: HomeComponent},
  { path: 'login', component: LoginComponent},
  { path: 'learn', component: LearnComponent, canActivate: [AuthGuard]},
  { path: 'searchResults', component: SearchResultComponent, canActivate: [AuthGuard]},
  { path: 'movieSearch', component: MovieSearchComponent, canActivate: [AuthGuard]},
  { path: 'idiomSearch', component: IdiomDetectorComponent, canActivate: [AuthGuard]},
  { path: 'analyze', component: AnalyzeComponent, canActivate: [AuthGuard]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {
    onSameUrlNavigation: 'reload'
  })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
