import {  Pipe, PipeTransform } from '@angular/core';
import { Observable, of } from 'rxjs';
import { LexiService } from 'src/app/services/lexi.service';
import { LexiDetails } from 'src/app/models/lexi-details';
import { map } from 'rxjs/operators';

@Pipe({
  name: 'getDefinition',
  pure: true
})
export class IndefinitePronounPipe implements PipeTransform {

  constructor(private lexiService:LexiService ) { }

  transform(value: string, args?: any): any {
    return this.indefinitePronoun(value);
  }

  indefinitePronoun(text: string): string {
    text.replace('@',"Someone");
    return text;
  }

}
