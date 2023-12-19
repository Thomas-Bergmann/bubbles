import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { StoreModule } from '@ngrx/store';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatInputModule} from '@angular/material/input';
import {MatSelectModule} from '@angular/material/select';
import {MatFormFieldModule} from '@angular/material/form-field';

import * as store from './store';
import * as service from './service';
import * as component from './components';
import { HumanModule } from '../humans';

@NgModule({
  declarations: [
    component.AddFixRelationComponent,
  ],
  imports: [
    CommonModule,
    FormsModule, ReactiveFormsModule, HttpClientModule,
    MatFormFieldModule, MatSelectModule, MatInputModule,
    StoreModule.forFeature(store.relationFeatureKey, store.relationReducer),
    HumanModule,
  ],
  providers: [
    service.RelationFacade,
    service.RelationService,
  ],
  exports: [
    component.AddFixRelationComponent,
  ]
})

export class RelationsModule {}
