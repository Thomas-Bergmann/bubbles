import { Component } from '@angular/core';

import { Human, HumanFacade } from 'src/app/humans';

@Component({
  selector: 'addHuman',
  templateUrl: './addHuman.component.html',
  styleUrls: ['./addHuman.component.sass']
})
export class AddHumanComponent {
  nameField = '';
  birthField = '';
  deathField = '';

  constructor(
    private readonly humanFacade: HumanFacade,
  ) {
  }

  _addHuman() {
    this.humanFacade.createHuman(
      new Human().newHuman(this.nameField, this.birthField, this.deathField));
      this.nameField = '';
  }
}
