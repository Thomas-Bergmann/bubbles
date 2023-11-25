import { Store } from '@ngrx/store';
import { Injectable } from '@angular/core';

import { HumanService } from './human.service';
import { HumanState, Human, addHumans, addHuman, removeHuman } from 'src/app/humans/store';
import { OIDCState, selectCurrentUser } from 'src/app/oidc';
import { ServiceEndpoint, ServiceState, updateServiceLocation } from 'src/app/core/service';
import { environment } from 'src/environments/environment';

@Injectable({ providedIn: 'root' })
export class HumanFacade {
  areHumansLoaded : Map<string, boolean> = new Map();
  userRef: string = "";
  constructor(
    private readonly store: Store<HumanState>,
    private readonly service : HumanService,
    private readonly serviceStore: Store<ServiceState>,
    private readonly oidcStore: Store<OIDCState>,
  ) {
    oidcStore.select(selectCurrentUser).subscribe(u => {
      if (u !== null && u !== "")
      {
        this.userRef = u;
      }
    });
  }

  loadHumans() {
    if (this.userRef === "")
    {
      return;
    }
    if (this.areHumansLoaded.get(this.userRef))
    {
      return;
    }
    this.serviceStore.dispatch(updateServiceLocation(
      { serviceEndpoint : new ServiceEndpoint().init(environment.serviceEndPoint)}));
    this.areHumansLoaded.set(this.userRef, true);
    this.service.getHumans(this.userRef)
      .subscribe(humans => {
        this.store.dispatch(addHumans({ humans : humans}));
        this.areHumansLoaded.delete(this.userRef);
      });
  }
  createHuman(human: Human) {
    this.store.dispatch(addHuman({ human : human}));
    this.service.addHuman(human.localRef, { name : human.name, userRef : this.userRef, dateOfBirth: human.dateOfBirth, dateOfDeath: human.dateOfDeath })
      .subscribe(() => this.getHuman(human.localRef));
  }
  getHuman(localRef: string) {
    this.service.getHuman(localRef)
      .subscribe(human => this.store.dispatch(addHuman({ human : human})));
  }
  updateHuman(human: Human) {
    this.store.dispatch(addHuman({ human : human}));
    this.service.updateHuman(human.localRef, { name : human.name, dateOfBirth: human.dateOfBirth, dateOfDeath: human.dateOfDeath, gender: human.gender })
      .subscribe(() => this.getHuman(human.localRef));
  }
  deleteHuman(human: Human) {
    this.store.dispatch(removeHuman({ human : human}));
    this.service.deleteHuman(human).subscribe();
  }
}
