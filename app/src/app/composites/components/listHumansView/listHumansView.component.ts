import { Component, OnInit} from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable, Unsubscribable } from 'rxjs';

import { HumanFacade, HumanState, Human, selectAllHumans, listHumansComponentOptionCard } from 'src/app/humans';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'listHumansView',
  templateUrl: './listHumansView.component.html',
  styleUrls: ['./listHumansView.component.sass']
})

export class ListHumansViewComponent implements OnInit  {
  allHumans$: Observable<Human[]>;
  humans: readonly Human[] = [];
  unsubscribeOnDestroy : Unsubscribable[] = [];
  displayOption = listHumansComponentOptionCard;

  constructor(
    private readonly humanStore: Store<HumanState>,
    private readonly humanFacade: HumanFacade,
    private readonly router: Router,
    private readonly route: ActivatedRoute,
  ) {
    this.allHumans$ = this.humanStore.select(selectAllHumans);
  }
  ngOnInit(): void {
    this.humanFacade.loadHumans();
    this.allHumans$.subscribe(allHumans => {
      this.humans = Array.from(allHumans?.values());
    }); 
  }
  ngOnDestroy() {
    this.unsubscribeOnDestroy.forEach(s => s.unsubscribe());
  }

  public onSelectHuman(p: Human): void {
    this.router.navigate([p.localRef], { relativeTo: this.route.parent });
  }
}
