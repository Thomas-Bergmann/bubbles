import { Component, Input } from '@angular/core';

import { Human, listHumansComponentOptionCanvas } from 'src/app/humans';
import { RelationFacade } from 'src/app/relations/service';

@Component({
  selector: 'addFixRelation',
  templateUrl: './addFixRelation.component.html',
  styleUrls: ['./addFixRelation.component.sass']
})
export class AddFixRelationComponent {
  @Input() human!: Human;
  @Input() humans!: readonly Human[];
  @Input() displayOption : string = listHumansComponentOptionCanvas;

  constructor(
    private readonly relationFacade: RelationFacade,
  ) {
  }

  public onSelectHuman(otherHuman:Human) {
    this.relationFacade.createChild(this.human, otherHuman);
  }
}
