import { createAction, props } from '@ngrx/store';
import { Human } from './human-models';

export const addHumans = createAction(
  'Add Humans',
  props<{ humans: Human[] }>()
);

export const addHuman = createAction(
  'Add Human',
  props<{ human: Human }>()
);

export const removeHuman = createAction(
  'Remove Human',
  props<{ human: Human }>()
);
