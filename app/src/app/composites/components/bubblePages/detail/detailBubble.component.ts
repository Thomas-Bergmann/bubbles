import { Component, OnInit} from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable, Unsubscribable } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';

import { BubbleFacade, BubbleState, Bubble, selectAllBubbles } from 'src/app/bubbles';

@Component({
  templateUrl: './detailBubble.component.html',
  styleUrls: ['./detailBubble.component.sass']
})

export class DetailBubblePage implements OnInit  {
  allBubbles$: Observable<ReadonlyMap<string, Bubble>>;
  bubbles: readonly Bubble[] | undefined;
  unsubscribeOnDestroy : Unsubscribable[] = [];

  routeBubble? : string;
  selectedBubble? : Bubble;

  constructor(
    private readonly bubbleStore: Store<BubbleState>,
    private readonly bubbleFacade: BubbleFacade,
    private readonly router: Router,
    private readonly route: ActivatedRoute,
  ) {
    this.allBubbles$ = this.bubbleStore.select(selectAllBubbles);
  }
  ngOnChanges() : void {
    this.selectedBubble = undefined;
    this.sureThatEveryThingIsLoaded();
  }
  ngOnInit(): void {
    this.unsubscribeOnDestroy.push(this.route.params.subscribe(params => {
      // console.log("route", params);
      this.routeBubble = params['bubble'];
      this.selectedBubble = undefined;
      this.sureThatEveryThingIsLoaded();
    }));
  }
  ngOnDestroy() {
    this.unsubscribeOnDestroy.forEach(s => s.unsubscribe());
  }

  public onSelectBubble(p: Bubble): void {
    this.router.navigate([p.name], { relativeTo: this.route.parent?.parent });
  }
  
  private sureThatEveryThingIsLoaded() {
    this.makeSureBubbleIsLoaded();
  }

  private makeSureBubbleIsLoaded() {
    if (this.bubbles === undefined)
    {
      // this.bubbleFacade.loadBubbles();
      return;
    }
    this.bubbles
      .filter(p => this.routeBubble == p.localRef)
      .forEach(p => {
        if (this.selectedBubble !== p) {
          this.selectedBubble = p;
          this.sureThatEveryThingIsLoaded();
        }
    });
  }
}
