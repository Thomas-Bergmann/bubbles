import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule} from '@angular/forms';
import * as composites from './components';

import { BubbleModule } from '../bubbles';
import { CompositeRoutingModule } from './routing';


@NgModule({
  declarations: [
    composites.ListBubblesViewComponent,
    composites.DetailBubbleViewComponent,
  ],
  imports: [
    CommonModule,
    FormsModule, ReactiveFormsModule, HttpClientModule,
    CompositeRoutingModule,
    BubbleModule,
  ],
  providers: [],
  exports: []
})

export class CompositeModule {}
