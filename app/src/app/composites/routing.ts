import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from '../oidc/guard';
import * as composites from './components';

const routes: Routes = [
//  { path: 'players', component: composites.ListPlayersViewComponent },
//  { path: 'players/:player', component: composites.DetailPlayerViewComponent },
//  { path: 'players/:player/seats/:seat', component: composites.GameBubbleViewComponent },
{
  path: 'bubbles',
  canLoad: [AuthGuard],
  canActivate: [AuthGuard],
  children: [
    { path: '',       pathMatch: 'full', component: composites.ListBubblesViewComponent },
    { path: ':bubble', pathMatch: 'prefix',
      children: [
        { path: '',          pathMatch: 'full', component: composites.DetailBubbleViewComponent },
      ]
    }
  ]
},
{
  path: 'humans',
  canLoad: [AuthGuard],
  canActivate: [AuthGuard],
  children: [
    { path: '',       pathMatch: 'full', component: composites.ListHumansViewComponent },
    { path: ':human', pathMatch: 'prefix',
      children: [
        { path: '',          pathMatch: 'full', component: composites.DetailHumanViewComponent },
      ]
    }
  ]
},
];

@NgModule({
  imports: [
    RouterModule.forChild(routes)
  ],
  exports: [
    RouterModule
  ]
})
export class CompositeRoutingModule {}


/*
Copyright Google LLC. All Rights Reserved.
Use of this source code is governed by an MIT-style license that
can be found in the LICENSE file at https://angular.io/license
*/