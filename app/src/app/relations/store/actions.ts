import { createAction, props } from '@ngrx/store';
import { Relation } from './models';

export const addChild = createAction(
  'Add Child',
  props<{ parent: string, child: string }>()
);

export const addRelations = createAction(
  'Add Relations',
  props<{ relations: Relation[] }>()
);

export const removeRelation = createAction(
  'Remove Relation',
  props<{ relation: Relation }>()
);
