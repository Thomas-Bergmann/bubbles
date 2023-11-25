import { createSelector, createFeatureSelector } from '@ngrx/store';

import { serviceFeatureKey, ServiceState } from './service-reducers';

const selectFeature = createFeatureSelector<ServiceState>(serviceFeatureKey);

export const restEndpoint = createSelector(
  selectFeature,
  (state: ServiceState) => state.serviceEndpoint.uri
);

export const selectClientError = createSelector(
  selectFeature,
  (state: ServiceState) => state.clientErrors
);
