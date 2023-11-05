import { Component, EventEmitter, Input, Output} from '@angular/core';

import { Human } from 'src/app/humans/store';

@Component({
  selector: 'listHumans',
  templateUrl: './listHumans.component.html',
  styleUrls: ['./listHumans.component.sass']
})
export class ListHumansComponent {
  @Input() humans!: readonly Human[];
  @Input() selectedHuman? : Human;
  @Output() selectHuman = new EventEmitter<Human>();

  public select(p: Human): void {
    this.selectHuman.emit(p);
  }
}
