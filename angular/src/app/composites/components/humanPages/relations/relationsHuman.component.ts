import { Component, OnChanges, OnInit} from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable, Unsubscribable } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';

import { HumanFacade, HumanState, Human, selectAllHumans, listHumansComponentOptionCanvas, listHumansComponentOptionCard } from 'src/app/humans';
import { RelationFacade, RelationState, RelationType, getRelationsOfHuman, selectAllRelations } from 'src/app/relations';

@Component({
  templateUrl: './relationsHuman.component.html',
  styleUrls: ['./relationsHuman.component.sass']
})

export class RelationsHumanPage implements OnInit {
  humans: readonly Human[]  = [];
  unsubscribeOnDestroy : Unsubscribable[] = [];
  routeHuman? : string;
  selectedHuman? : Human;
  children?: readonly Human[]  = undefined;
  parents?: readonly Human[]  = undefined;
  displayOption = listHumansComponentOptionCanvas;

  constructor(
    private readonly humanStore: Store<HumanState>,
    private readonly humanFacade: HumanFacade,
    private readonly relationStore: Store<RelationState>,
    private readonly relationFacade: RelationFacade,
    private readonly router: Router,
    private readonly route: ActivatedRoute,
  ) {
  }
  ngOnInit(): void {
    this.unsubscribeOnDestroy.push(this.route.params.subscribe(params => {
      console.log("route", params);
      this.routeHuman = params['human'].replace('%20', ' ');
      this.selectedHuman = undefined;
      this.parents = undefined;
      this.children = undefined;
      this.sureThatEveryThingIsLoaded();
    }));
    this.unsubscribeOnDestroy.push(this.humanStore.select(selectAllHumans).subscribe(humans => {
      this.humans = humans;
      this.sureThatEveryThingIsLoaded();
    }));
    this.unsubscribeOnDestroy.push(this.relationStore.select(selectAllRelations).subscribe(allRelations => {
      if (this.selectedHuman !== undefined)
      {
        // get children
        let children_relations = getRelationsOfHuman(allRelations, this.selectedHuman, RelationType.CHILD);
        let children = new Array();
        children_relations.forEach(relation => {
          this.humans.filter(h => h.localRef == relation.human2LocalRef).forEach(h => children.push(h));
        })
        this.children = children;
        // get parents
        let parent_relations = getRelationsOfHuman(allRelations, this.selectedHuman, RelationType.PARENT);
        let parents = new Array();
        parent_relations.forEach(relation => {
          this.humans.filter(h => h.localRef == relation.human2LocalRef).forEach(h => parents.push(h));
        })
        this.parents = parents;
        this.sureThatEveryThingIsLoaded();
      }
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
    this.makeSureChildrenAreLoaded();
    this.makeSureParentsAreLoaded();
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
  private makeSureChildrenAreLoaded() {
    if (this.selectedHuman === undefined)
    {
      return;
    }
    if (this.children === undefined)
    {
      this.relationFacade.loadChildren(this.selectedHuman);
    }
  }
  private makeSureParentsAreLoaded() {
    if (this.selectedHuman === undefined)
    {
      return;
    }
    if (this.parents === undefined)
    {
      this.relationFacade.loadParents(this.selectedHuman);
    }
  }
  
  _onChild(human:Human) {
    this.router.navigate([human.localRef, "relations"], { relativeTo: this.route.parent?.parent });
  }
  _onParent(human:Human) {
    this.router.navigate([human.localRef, "relations"], { relativeTo: this.route.parent?.parent });
  }
  _onHuman(human:Human) {
    this.router.navigate([human.localRef, "relations"], { relativeTo: this.route.parent?.parent });
  }
}
