import { Component, Input } from '@angular/core';

import { Human } from 'src/app/humans/store/human-models';
@Component({
  selector: 'cardHuman',
  templateUrl: './cardHuman.component.html',
  styleUrls: ['./cardHuman.component.sass']
})
export class CardHumanComponent {
  @Input() human!: Human;
  @Input() selectedHuman?: Human;
}
