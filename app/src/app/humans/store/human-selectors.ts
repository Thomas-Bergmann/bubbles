import { createSelector, createFeatureSelector } from '@ngrx/store';

import { Human } from './human-models';
import { humanFeatureKey, HumanState } from './human-reducers';

const selectFeature = createFeatureSelector<HumanState>(humanFeatureKey);

export const selectAllHumans = createSelector(selectFeature, getAllHumansFromState);
function getAllHumansFromState (state: HumanState) : Human[]
{
  return Array.from(state.humans.values());;
}
