import { Action, createReducer, on } from '@ngrx/store';

import { Relation, RelationType } from './models';
import { addRelations, addChild, removeRelation } from './actions';

export const relationFeatureKey = 'relationState';

// keys human1 and human2 are keys
var initialRelations : ReadonlyMap<string, ReadonlyMap<string, ReadonlyArray<Relation>>> = new Map();
export interface RelationState {
  relations: ReadonlyMap<string, ReadonlyMap<string, ReadonlyArray<Relation>>>;
}

const initialState : RelationState = {
  relations : initialRelations,
}

const _relationReducer = createReducer(
  initialState,
  on(addChild, (state, action) => _updateRelationsAtState(state, _addChild(state.relations, action.parent, action.child))),
  on(addRelations, (state, action) => _updateRelationsAtState(state, _setRelations(state.relations, action.relations))),
  on(removeRelation, (state, action) => _updateRelationsAtState(state, _removeRelation(state.relations, action.relation))),
);

export function relationReducer(state: RelationState | undefined, action: Action) {
  return _relationReducer(state, action);
}

function _updateRelationsAtState(state:RelationState, newRelations: ReadonlyMap<string, ReadonlyMap<string, ReadonlyArray<Relation>>>):RelationState
{
  return ({
    ...state,
    relations: newRelations,
  });
}

function _setRelation(relations:ReadonlyMap<string, ReadonlyMap<string, ReadonlyArray<Relation>>>, relation:Relation):ReadonlyMap<string, ReadonlyMap<string, ReadonlyArray<Relation>>>
{
  // copy outer map
  let newRelationsAll : Map<string, ReadonlyMap<string, ReadonlyArray<Relation>>> = new Map();
  relations?.forEach((v,k) => newRelationsAll.set(k,v));
  // copy inner map
  let relations1 = newRelationsAll.get(relation.human1LocalRef);
  let newRelations1 : Map<string, readonly Relation[]> = new Map();
  relations1?.forEach((v,k) => newRelations1.set(k,v));
  // copy array of relations between two humans
  let relations2 = newRelations1.get(relation.human2LocalRef);
  let newRelations2 : Array<Relation> = new Array();
  // add existing relation to array with different type
  relations2?.filter(e => relation.type != e.type).forEach(e => newRelations2.push(e));
  // add new relation to array
  newRelations2.push(relation);
  // replace array of relations between same humans
  newRelations1.set(relation.human2LocalRef, newRelations2)
  newRelationsAll.set(relation.human1LocalRef, newRelations1);
  // update relation
  return newRelationsAll;
}

function _setRelations(relations:ReadonlyMap<string, ReadonlyMap<string, ReadonlyArray<Relation>>>,  setRelations:ReadonlyArray<Relation>):ReadonlyMap<string, ReadonlyMap<string, ReadonlyArray<Relation>>>
{
  let newRelations = relations;
  setRelations.forEach(relation => {
    newRelations = _setRelation(newRelations, relation);
  });
  return newRelations;
}

function _removeRelation(relations : ReadonlyMap<string, ReadonlyMap<string, ReadonlyArray<Relation>>>, relation:Relation):ReadonlyMap<string, ReadonlyMap<string, ReadonlyArray<Relation>>>
{
  // copy outer map
  let newRelationsAll : Map<string, ReadonlyMap<string, ReadonlyArray<Relation>>> = new Map();
  relations?.forEach((v,k) => newRelationsAll.set(k,v));
  // copy inner map
  let relations1 = newRelationsAll.get(relation.human1LocalRef);
  let newRelations1 : Map<string, readonly Relation[]> = new Map();
  relations1?.forEach((v,k) => newRelations1.set(k,v));
  // copy array of relations between two humans
  let relations2 = newRelations1.get(relation.human2LocalRef);
  let newRelations2 : Array<Relation> = new Array();
  // don't copy relation of same type to new array, will remove the given relation
  relations2?.filter(e => relation.type != e.type).forEach(e => newRelations2.push(e));
  // replace array of relations between same humans
  newRelations1.set(relation.human2LocalRef, newRelations2)
  newRelationsAll.set(relation.human1LocalRef, newRelations1);
  // update relation
  return newRelationsAll;
}

function _addChild(relations:ReadonlyMap<string, ReadonlyMap<string, ReadonlyArray<Relation>>>, parent:string, child:string):ReadonlyMap<string, ReadonlyMap<string, ReadonlyArray<Relation>>>
{
  let relation1 = new Relation().newRelation(parent, child, RelationType.CHILD);
  let relation2 = new Relation().newRelation(child, parent, RelationType.PARENT);
  return _setRelations(relations, [relation1, relation2]);
}
