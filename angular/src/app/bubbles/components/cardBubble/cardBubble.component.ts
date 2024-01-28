import { Component, Input } from '@angular/core';

import { Bubble } from 'src/app/bubbles/store/bubble-models';
@Component({
  selector: 'cardBubble',
  templateUrl: './cardBubble.component.html',
  styleUrls: ['./cardBubble.component.sass']
})
export class CardBubbleComponent {
  @Input() bubble!: Bubble;
  @Input() selectedBubble?: Bubble;
}
