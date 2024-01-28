import { createAction, props } from '@ngrx/store';
import { ServiceError, ServiceEndpoint} from '.';

export const serverTimeout = createAction('Server Timeout');
export const clientError = createAction('Client Error', props<{error : ServiceError}>());
export const serverError = createAction('Server Error', props<{error : ServiceError}>());
export const updateServiceLocation = createAction('Update Service Endpoint', props<{ serviceEndpoint: ServiceEndpoint }>());
export const clearClientErrors = createAction('Clear Client Errors');
