import { Component, EventEmitter, Input, OnChanges, Output, SimpleChanges} from '@angular/core';

import { Human } from 'src/app/humans/store';

export const listHumansComponentOptionCard = "card";
export const listHumansComponentOptionCanvas = "canvas";

@Component({
  selector: 'listHumans',
  templateUrl: './listHumans.component.html',
  styleUrls: ['./listHumans.component.sass']
})
export class ListHumansComponent implements OnChanges {
  @Input() humans!: readonly Human[];
  @Input() selectedHuman? : Human;
  @Input() displayOption : string = listHumansComponentOptionCard;
  @Output() selectHuman = new EventEmitter<Human>();
  showCard : boolean = true;
  showCanvas : boolean = false;

  ngOnChanges(changes: SimpleChanges): void {
    this.showCard = this.displayOption == listHumansComponentOptionCard;
    this.showCanvas = this.displayOption == listHumansComponentOptionCanvas;
  }
  public select(p: Human): void {
    this.selectHuman.emit(p);
  }
}
