import { createSelector, createFeatureSelector } from '@ngrx/store';

import { Bubble } from './bubble-models';
import { bubbleFeatureKey, BubbleState } from './bubble-reducers';

const selectFeature = createFeatureSelector<BubbleState>(bubbleFeatureKey);

export const selectAllBubbles = createSelector(selectFeature, getAllBubblesFromState);
function getAllBubblesFromState (state: BubbleState) : ReadonlyMap<string, Bubble>
{
  return state.bubbles;
}
