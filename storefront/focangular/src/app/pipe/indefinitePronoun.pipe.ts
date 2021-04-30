import { Pipe, PipeTransform } from '@angular/core';
import { Observable, of } from 'rxjs';
import { LexiService } from 'src/app/services/lexi.service';
import { LexiDetails } from 'src/app/models/lexi-details';
import { map } from 'rxjs/operators';

@Pipe({
  name: 'indefinitepronoun',
  pure: true,
})
export class IndefinitePronounPipe implements PipeTransform {
  constructor(private lexiService: LexiService) {}

  transform(value: String, args?: any): any {
    if (typeof value === 'undefined' || value === null) {
      return value;
    }
    return this.indefinitePronoun(value);
  }

  indefinitePronoun(text: String): String {
    /* One's	@
Something	#
Someone	##
Somewhere	###
oneself	@@ */
    return text
      .toString()
      .replace('@@', 'Oneself')
      .replace('@', "One's")
      .replace('###', 'Somewhere')
      .replace('##', 'Someone')
      .replace('#', 'Something');
  }
}
