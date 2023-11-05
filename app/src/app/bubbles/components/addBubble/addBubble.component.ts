import { Component } from '@angular/core';

import { Bubble, BubbleFacade } from 'src/app/bubbles';

@Component({
  selector: 'addBubble',
  templateUrl: './addBubble.component.html',
  styleUrls: ['./addBubble.component.sass']
})
export class AddBubbleComponent {
  nameField = '';

  constructor(
    private readonly bubbleFacade: BubbleFacade,
  ) {
  }

  _addBubble() {
    this.bubbleFacade.createBubble(
      new Bubble().newBubble(this.nameField));
      this.nameField = '';
  }
}
