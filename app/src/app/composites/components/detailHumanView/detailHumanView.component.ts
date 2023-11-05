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
  allHumans$: Observable<ReadonlyMap<string, Human>>;
  humans: readonly Human[] | undefined;
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
      this.routeHuman = params['human'];
      this.selectedHuman = undefined;
      this.sureThatEveryThingIsLoaded();
    }));
  }
  ngOnDestroy() {
    this.unsubscribeOnDestroy.forEach(s => s.unsubscribe());
  }

  public onSelectHuman(p: Human): void {
    this.router.navigate([p.name], { relativeTo: this.route.parent?.parent });
  }
  
  private sureThatEveryThingIsLoaded() {
    this.makeSureHumanIsLoaded();
  }

  private makeSureHumanIsLoaded() {
    if (this.humans === undefined)
    {
      // this.humanFacade.loadHumans();
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
}
