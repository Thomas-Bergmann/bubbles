import { Component, EventEmitter, Input, OnChanges, Output } from '@angular/core';
import { Human } from 'src/app/humans';

@Component({
  selector: 'deleteHuman',
  templateUrl: './deleteHuman.component.html',
  styleUrls: ['./deleteHuman.component.sass'],
})
export class DeleteHumanComponent {
  @Input() human!: Human;
  @Output() deleteHuman = new EventEmitter<Human>();
  
  _deleteHuman() {
    this.deleteHuman.emit(this.human);
  }
}
