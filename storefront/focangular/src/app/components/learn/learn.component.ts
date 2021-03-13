import { Component, OnInit } from '@angular/core';
import {LearnService} from '../../services/learn.service';
import { Example } from 'src/app/models/example';
import {OverlayService} from '../../services/overlay.service';
import { SpinnerComponent } from '../spinner/spinner.component';
import { OverlayRef } from '@angular/cdk/overlay';

@Component({
  selector: 'app-learn',
  templateUrl: './learn.component.html',
  styleUrls: ['./learn.component.css']
})
export class LearnComponent implements OnInit {

  example:Example;
  
  constructor(private learnService:LearnService,private overlayService:OverlayService ) { 
  }

  ngOnInit() {
    const learnObservable = this.learnService.fetchAnExample();
    const spinner:OverlayRef=this.overlayService.open({ hasBackdrop: true },SpinnerComponent);

    learnObservable .subscribe((data:Example) => 
      {
        this.example=data;
        this.overlayService.close(spinner);

      }
      );
  }

}
