import { Component, OnInit} from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable, Unsubscribable } from 'rxjs';

import { BubbleFacade, BubbleState, Bubble, selectAllBubbles } from 'src/app/bubbles';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  templateUrl: './listBubbles.component.html',
  styleUrls: ['./listBubbles.component.sass']
})

export class ListBubblesPage implements OnInit  {
  allBubbles$: Observable<ReadonlyMap<string, Bubble>>;
  bubbles: readonly Bubble[] = [];
  unsubscribeOnDestroy : Unsubscribable[] = [];

  constructor(
    private readonly bubbleStore: Store<BubbleState>,
    private readonly bubbleFacade: BubbleFacade,
    private readonly router: Router,
    private readonly route: ActivatedRoute,
  ) {
    this.allBubbles$ = this.bubbleStore.select(selectAllBubbles);
  }
  ngOnInit(): void {
    this.bubbleFacade.loadBubbles();
    this.allBubbles$.subscribe(allBubbles => {
      this.bubbles = Array.from(allBubbles?.values());
    }); 
  }
  ngOnDestroy() {
    this.unsubscribeOnDestroy.forEach(s => s.unsubscribe());
  }

  public onSelectBubble(p: Bubble): void {
    this.router.navigate([p.name], { relativeTo: this.route.parent });
  }
}
