import { Action, createReducer, on } from '@ngrx/store';
import { Bubble } from './bubble-models';
import { addBubbles, addBubble, removeBubble, updateBubble } from './bubble-actions';

export const bubbleFeatureKey = 'bubbleState';

// keys tenantid and bubbleid
var initialBubbles : ReadonlyMap<string, Bubble> = new Map();
export interface BubbleState {
  bubbles: ReadonlyMap<string, Bubble>;
}

const initialState : BubbleState = {
  bubbles : initialBubbles,
}

const _bubbleReducer = createReducer(
  initialState,
  on(addBubbles, (state, action) => _updateBubblesAtState(state, _setBubbles(state.bubbles, action.bubbles))),
  on(addBubble, (state, action) => _updateBubblesAtState(state, _setBubble(state.bubbles, action.bubble))),
  on(removeBubble, (state, action) => _updateBubblesAtState(state, _removeBubble(state.bubbles, action.bubble))),
  on(updateBubble, (state, action) => _updateBubblesAtState(state, _setBubble(state.bubbles, action.bubble))),
);

export function bubbleReducer(state: BubbleState | undefined, action: Action) {
  return _bubbleReducer(state, action);
}

function _updateBubblesAtState(state:BubbleState, newBubbles: ReadonlyMap<string, Bubble>):BubbleState
{
  return ({
    ...state,
    bubbles: newBubbles,
  });
}

function _setBubble(bubbles:ReadonlyMap<string, Bubble>,  bubble:Bubble):ReadonlyMap<string, Bubble>
{
  let newBubbles : Map<string, Bubble> = new Map();
  bubbles?.forEach((v,k) => newBubbles.set(k,v));
  newBubbles.set(bubble.resourceURI, bubble);
  return newBubbles;
}

function _setBubbles(bubbles:ReadonlyMap<string, Bubble>,  setBubbles:Bubble[]):ReadonlyMap<string, Bubble>
{
  let newBubbles : ReadonlyMap<string, Bubble> = bubbles;
  setBubbles.forEach(bubble => {
    newBubbles = _setBubble(newBubbles, bubble);
  });
  return newBubbles;
}

function _removeBubble(bubbles : ReadonlyMap<string, Bubble>, bubble:Bubble):ReadonlyMap<string, Bubble>
{
  let newBubbles : Map<string, Bubble> = new Map();
  bubbles?.forEach((v,k) => newBubbles.set(k,v));
  newBubbles.delete(bubble.resourceURI);
  return newBubbles;
}
