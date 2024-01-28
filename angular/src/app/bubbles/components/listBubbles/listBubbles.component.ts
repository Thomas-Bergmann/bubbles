import { Component, EventEmitter, Input, Output} from '@angular/core';

import { Bubble } from 'src/app/bubbles/store';

@Component({
  selector: 'listBubbles',
  templateUrl: './listBubbles.component.html',
  styleUrls: ['./listBubbles.component.sass']
})
export class ListBubblesComponent {
  @Input() bubbles!: readonly Bubble[];
  @Input() selectedBubble? : Bubble;
  @Output() selectBubble = new EventEmitter<Bubble>();

  public select(p: Bubble): void {
    this.selectBubble.emit(p);
  }
}
