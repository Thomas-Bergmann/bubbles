import { Action, createReducer, on } from '@ngrx/store';
import { Human } from './human-models';
import { addHumans, addHuman, removeHuman, updateHuman } from './human-actions';

export const humanFeatureKey = 'humanState';

// keys tenantid and humanid
var initialHumans : ReadonlyMap<string, Human> = new Map();
export interface HumanState {
  humans: ReadonlyMap<string, Human>;
}

const initialState : HumanState = {
  humans : initialHumans,
}

const _humanReducer = createReducer(
  initialState,
  on(addHumans, (state, action) => _updateHumansAtState(state, _setHumans(state.humans, action.humans))),
  on(addHuman, (state, action) => _updateHumansAtState(state, _setHuman(state.humans, action.human))),
  on(removeHuman, (state, action) => _updateHumansAtState(state, _removeHuman(state.humans, action.human))),
  on(updateHuman, (state, action) => _updateHumansAtState(state, _setHuman(state.humans, action.human))),
);

export function humanReducer(state: HumanState | undefined, action: Action) {
  return _humanReducer(state, action);
}

function _updateHumansAtState(state:HumanState, newHumans: ReadonlyMap<string, Human>):HumanState
{
  return ({
    ...state,
    humans: newHumans,
  });
}

function _setHuman(humans:ReadonlyMap<string, Human>,  human:Human):ReadonlyMap<string, Human>
{
  let newHumans : Map<string, Human> = new Map();
  humans?.forEach((v,k) => newHumans.set(k,v));
  newHumans.set(human.resourceURI, human);
  return newHumans;
}

function _setHumans(humans:ReadonlyMap<string, Human>,  setHumans:Human[]):ReadonlyMap<string, Human>
{
  let newHumans : ReadonlyMap<string, Human> = humans;
  setHumans.forEach(human => {
    newHumans = _setHuman(newHumans, human);
  });
  return newHumans;
}

function _removeHuman(humans : ReadonlyMap<string, Human>, human:Human):ReadonlyMap<string, Human>
{
  let newHumans : Map<string, Human> = new Map();
  humans?.forEach((v,k) => newHumans.set(k,v));
  newHumans.delete(human.resourceURI);
  return newHumans;
}
