import { Component, EventEmitter, Input, OnChanges, OnInit, Output } from '@angular/core';

import { Human, HumanFacade } from 'src/app/humans';

@Component({
  selector: 'editHuman',
  templateUrl: './editHuman.component.html',
  styleUrls: ['./editHuman.component.sass']
})
export class EditHumanComponent implements OnChanges {
  @Input() human!: Human;
  @Output() updateHuman = new EventEmitter<Human>();
  nameField = '';
  birthField = '';
  deathField = '';

  ngOnChanges(): void {
    this.nameField = this.human.name;
    this.birthField = this.human.dateOfBirth || '';
    this.deathField = this.human.dateOfDeath || '';
  }

  _updateHuman() {
    this.human.dateOfBirth = this.birthField;
    this.human.dateOfDeath = this.deathField;
    this.human.name = this.nameField;
    this.updateHuman.emit(this.human);
  }
}
