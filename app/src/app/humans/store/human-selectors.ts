import { createSelector, createFeatureSelector } from '@ngrx/store';

import { Human } from './human-models';
import { humanFeatureKey, HumanState } from './human-reducers';

const selectFeature = createFeatureSelector<HumanState>(humanFeatureKey);

export const selectAllHumans = createSelector(selectFeature, getAllHumansFromState);
function getAllHumansFromState (state: HumanState) : ReadonlyMap<string, Human>
{
  return state.humans;
}
