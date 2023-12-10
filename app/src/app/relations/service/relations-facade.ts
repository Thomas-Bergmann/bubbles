import { Store } from '@ngrx/store';
import { Injectable } from '@angular/core';

import { RelationService } from './relations-service';
import { Relation, RelationState, addChild, removeRelation } from '../store';
import { Human, HumanFacade} from 'src/app/humans';

@Injectable({ providedIn: 'root' })
export class RelationFacade {
  areHumansByChildLoaded : Map<string, boolean> = new Map();
  constructor(
    private readonly store: Store<RelationState>,
    private readonly service : RelationService,
    private readonly humanFacade : HumanFacade,
  ) {
  }
  createChild(parent: Human, child : Human) {
    this.store.dispatch(addChild({ parent : parent.localRef, child : child.localRef}));
    this.service.createChild(parent.localRef, child.localRef).subscribe();
  }
  loadChildren(parent: Human) {
    this.service.getChildren(parent.localRef).subscribe(children => {
      children.forEach(child => this.store.dispatch(addChild({ parent : parent.localRef, child : child})));
    });
  }
  loadParents(child: Human) {
    if (this.areHumansByChildLoaded.has(child.localRef))
    {
      return;
    }
    this.areHumansByChildLoaded.set(child.localRef, true);
    this.humanFacade.getParents(child.localRef).subscribe(parents => {
      parents.forEach(parent => this.store.dispatch(addChild({ parent : parent.localRef, child : child.localRef})));
      // console.log("got parents by child", child.name, parents);
      this.areHumansByChildLoaded.delete(child.localRef);
    });
  }
  deleteChild(relation: Relation) {
    this.store.dispatch(removeRelation({ relation : relation}));
    this.service.deleteChild(relation.human1LocalRef, relation.human2LocalRef).subscribe();
  }
}
