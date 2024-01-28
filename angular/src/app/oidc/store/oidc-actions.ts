import { createAction, props } from '@ngrx/store';
import { OIDCProvider, Token } from './oidc-models';

export const addProviders = createAction(
  'Add Providers',
  props<{ providers: OIDCProvider[] }>()
);

export const defineProvider = createAction(
  'Define Provider',
  props<{ provider: OIDCProvider }>()
);

export const defineUser = createAction(
  'Define User',
  props<{ user: string }>()
);

export const setAccessToken = createAction(
  'Set Access Token',
  props<Token>()
);
export const setRefreshToken = createAction(
  'Set Refresh Token',
  props<Token>()
);
export const addResources = createAction(
  'Add Resources',
  props<{ resources: string[] }>()
);
export const rememberRouteBeforeLogin = createAction(
  'Remember Route Before Login',
  props<{ url: string }>()
);
