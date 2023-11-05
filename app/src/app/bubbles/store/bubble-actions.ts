import { createAction, props } from '@ngrx/store';
import { Bubble } from './bubble-models';

export const addBubbles = createAction(
  'Add Bubbles',
  props<{ bubbles: Bubble[] }>()
);

export const addBubble = createAction(
  'Add Bubble',
  props<{ bubble: Bubble }>()
);

export const removeBubble = createAction(
  'Remove Bubble',
  props<{ bubble: Bubble }>()
);

export const updateBubble = createAction(
  'Update Bubble',
  props<{ bubble: Bubble }>()
)
