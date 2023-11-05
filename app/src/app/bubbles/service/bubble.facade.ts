import { Store } from '@ngrx/store';
import { Injectable } from '@angular/core';

import { BubbleService } from './bubble.service';
import { BubbleState, Bubble, addBubbles, addBubble, updateBubble, removeBubble } from 'src/app/bubbles/store';
import { OIDCState, selectCurrentUser } from 'src/app/oidc';
import { ServiceEndpoint, ServiceState, updateServiceLocation } from 'src/app/core/service';
import { environment } from 'src/environments/environment';

@Injectable({ providedIn: 'root' })
export class BubbleFacade {
  areBubblesLoaded : Map<string, boolean> = new Map();
  userRef: string = "";
  constructor(
    private readonly store: Store<BubbleState>,
    private readonly service : BubbleService,
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

  loadBubbles() {
    if (this.userRef === "")
    {
      return;
    }
    if (this.areBubblesLoaded.get(this.userRef))
    {
      return;
    }
    // console.log("loadBubbles bubble.serviceEndPoint", environment.serviceEndPoint);
    this.serviceStore.dispatch(updateServiceLocation(
      { serviceEndpoint : new ServiceEndpoint().init(environment.serviceEndPoint)}));
    this.areBubblesLoaded.set(this.userRef, true);
    this.service.getBubbles(this.userRef)
      .subscribe(bubbles => {
        this.store.dispatch(addBubbles({ bubbles : bubbles}));
        this.areBubblesLoaded.delete(this.userRef);
      });
  }
  createBubble(bubble: Bubble) {
    this.store.dispatch(addBubble({ bubble : bubble}));
    this.service.addBubble(bubble.abbreviation, { name : bubble.name, userRef : this.userRef }).subscribe();
  }
  deleteBubble(bubble: Bubble) {
    this.store.dispatch(removeBubble({ bubble : bubble}));
    this.service.deleteBubble(bubble).subscribe();
  }
}
