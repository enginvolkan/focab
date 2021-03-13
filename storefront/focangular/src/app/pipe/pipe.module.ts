import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IndefinitePronounPipe } from './indefinitePronoun.pipe';



@NgModule({
  declarations: [IndefinitePronounPipe],
  imports: [
    CommonModule
  ],
  exports: [IndefinitePronounPipe]
})
export class PipeModule { }
