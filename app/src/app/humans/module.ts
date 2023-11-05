import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { StoreModule } from '@ngrx/store';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule} from '@angular/forms';
import * as store from './store';
import * as service from './service';
import * as component from './components';

@NgModule({
  declarations: [
    component.AddHumanComponent,
    component.CardHumanComponent,
    component.ListHumansComponent,
  ],
  imports: [
    CommonModule,
    FormsModule, ReactiveFormsModule, HttpClientModule,
    StoreModule.forFeature(store.humanFeatureKey, store.humanReducer),
  ],
  providers: [
    service.HumanFacade,
    service.HumanService,
  ],
  exports: [
    component.AddHumanComponent,
    component.ListHumansComponent,
  ]
})

export class HumanModule {}
