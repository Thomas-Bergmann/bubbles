import { createSelector, createFeatureSelector } from '@ngrx/store';

import { Relation, RelationType } from './models';
import { relationFeatureKey, RelationState } from './reducers';
import { Human } from 'src/app/humans';

const selectFeature = createFeatureSelector<RelationState>(relationFeatureKey);

export const selectAllRelations = createSelector(selectFeature, getAllRelationsFromState);
function getAllRelationsFromState (state: RelationState) : ReadonlyMap<string, ReadonlyMap<string, ReadonlyArray<Relation>>>
{
  return state.relations;
}

export function getRelationsOfHuman(relations : ReadonlyMap<string, ReadonlyMap<string, ReadonlyArray<Relation>>>, human: Human, type: RelationType) : Relation[]
{
  let result : Array<Relation> = new Array();
  relations.get(human.localRef)?.forEach((v,k) => v.filter(r => type == r.type).forEach(r => result.push(r)));
  return result;
}
