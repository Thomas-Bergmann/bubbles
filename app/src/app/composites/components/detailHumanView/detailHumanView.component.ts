import { Component, OnInit} from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable, Unsubscribable } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';

import { HumanFacade, HumanState, Human, selectAllHumans } from 'src/app/humans';

@Component({
  selector: 'detailHumanView',
  templateUrl: './detailHumanView.component.html',
  styleUrls: ['./detailHumanView.component.sass']
})

export class DetailHumanViewComponent implements OnInit  {
  allHumans$: Observable<Human[]>;
  humans: readonly Human[]  = [];
  unsubscribeOnDestroy : Unsubscribable[] = [];

  routeHuman? : string;
  selectedHuman? : Human;

  constructor(
    private readonly humanStore: Store<HumanState>,
    private readonly humanFacade: HumanFacade,
    private readonly router: Router,
    private readonly route: ActivatedRoute,
  ) {
    this.allHumans$ = this.humanStore.select(selectAllHumans);
  }
  ngOnChanges() : void {
    this.selectedHuman = undefined;
    this.sureThatEveryThingIsLoaded();
  }
  ngOnInit(): void {
    this.unsubscribeOnDestroy.push(this.route.params.subscribe(params => {
      // console.log("route", params);
      this.routeHuman = params['human'].replace('%20', ' ');
      this.selectedHuman = undefined;
      this.sureThatEveryThingIsLoaded();
    }));
    this.unsubscribeOnDestroy.push(this.allHumans$.subscribe(humans => {
      this.humans = humans;
      this.sureThatEveryThingIsLoaded();
    }));
  }
  ngOnDestroy() {
    this.unsubscribeOnDestroy.forEach(s => s.unsubscribe());
  }

  public onSelectHuman(p: Human): void {
    this.router.navigate([p.localRef], { relativeTo: this.route.parent?.parent });
  }
  
  private sureThatEveryThingIsLoaded() {
    this.makeSureHumanIsLoaded();
  }

  private makeSureHumanIsLoaded() {
    if (this.humans.length == 0)
    {
      this.humanFacade.loadHumans();
      return;
    }
    this.humans
      .filter(p => this.routeHuman == p.localRef)
      .forEach(p => {
        if (this.selectedHuman !== p) {
          this.selectedHuman = p;
          this.sureThatEveryThingIsLoaded();
        }
    });
  }
  _onUpdateHuman(human:Human) {
    this.humanFacade.updateHuman(human);
  }
  _deleteSelectedHuman() {
    if (this.selectedHuman !== undefined)
    {
      this.humanFacade.deleteHuman(this.selectedHuman);
    }
  }
}
