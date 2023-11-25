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

@NgModule({
  declarations: [
    component.AddHumanComponent,
    component.EditHumanComponent,
    component.CardHumanComponent,
    component.ListHumansComponent,
  ],
  imports: [
    CommonModule,
    FormsModule, ReactiveFormsModule, HttpClientModule,
    MatFormFieldModule, MatSelectModule, MatInputModule,
    StoreModule.forFeature(store.humanFeatureKey, store.humanReducer),
  ],
  providers: [
    service.HumanFacade,
    service.HumanService,
  ],
  exports: [
    component.AddHumanComponent,
    component.EditHumanComponent,
    component.ListHumansComponent,
  ]
})

export class HumanModule {}
