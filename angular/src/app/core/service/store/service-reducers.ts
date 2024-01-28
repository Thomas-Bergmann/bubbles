import { Action, createReducer, on } from '@ngrx/store';
import { clearClientErrors, clientError, updateServiceLocation } from './service-actions';
import { ServiceEndpoint, ServiceError } from './service-models';

export const serviceFeatureKey = 'serviceState';

export interface ServiceState {
  serviceEndpoint: ServiceEndpoint,
  clientErrors : ServiceError[]
}

var initialServices : ServiceEndpoint = new ServiceEndpoint().empty();

const initialState : ServiceState = {
  serviceEndpoint : initialServices,
  clientErrors : []
}

const _serviceReducer = createReducer(
  initialState,
  on(updateServiceLocation, (state, action) => _updateEndpointAtState(state, action.serviceEndpoint)),
  on(clientError, (state, action) => _setClientError(state, action.error)),
  on(clearClientErrors, (state) => _clearClientErrors(state)),
);

export function serviceReducer(state: ServiceState | undefined, action: Action) {
  return _serviceReducer(state, action);
}

function _updateEndpointAtState(state:ServiceState, serviceEndpoint: ServiceEndpoint):ServiceState
{
  return ({
    ...state,
    serviceEndpoint: serviceEndpoint,
  });
}

function _setClientError(state:ServiceState, error: ServiceError):ServiceState
{
  return ({
    ...state,
    clientErrors: [error],
  });
}

function _clearClientErrors(state:ServiceState):ServiceState
{
  return ({
    ...state,
    clientErrors: [],
  });
}
