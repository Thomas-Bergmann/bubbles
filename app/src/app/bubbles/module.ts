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
    component.AddBubbleComponent,
    component.CardBubbleComponent,
    component.ListBubblesComponent,
  ],
  imports: [
    CommonModule,
    FormsModule, ReactiveFormsModule, HttpClientModule,
    StoreModule.forFeature(store.bubbleFeatureKey, store.bubbleReducer),
  ],
  providers: [
    service.BubbleFacade,
    service.BubbleService,
  ],
  exports: [
    component.AddBubbleComponent,
    component.ListBubblesComponent,
  ]
})

export class BubbleModule {}
