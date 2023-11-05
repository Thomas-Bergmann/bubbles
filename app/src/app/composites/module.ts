import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule} from '@angular/forms';
import * as composites from './components';

import { CompositeRoutingModule } from './routing';
import { BubbleModule } from '../bubbles';
import { HumanModule } from '../humans';


@NgModule({
  declarations: [
    composites.ListBubblesViewComponent,
    composites.DetailBubbleViewComponent,
    composites.ListHumansViewComponent,
    composites.DetailHumanViewComponent,
  ],
  imports: [
    CommonModule,
    FormsModule, ReactiveFormsModule, HttpClientModule,
    CompositeRoutingModule,
    BubbleModule,
    HumanModule,
  ],
  providers: [],
  exports: []
})

export class CompositeModule {}
