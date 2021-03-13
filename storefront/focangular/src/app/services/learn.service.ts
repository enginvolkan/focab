import { Injectable } from '@angular/core';
import {Example} from '../models/example';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { OverlayService } from './overlay.service';
import { SpinnerComponent } from '../components/spinner/spinner.component';
import { OverlayRef } from '@angular/cdk/overlay';

@Injectable({
  providedIn: 'root'
})
export class LearnService {

  constructor(private http: HttpClient) { }

  fetchAnExample():Observable<Example> {
    return this.http.get<Example>("/learn", {});
  }

}
