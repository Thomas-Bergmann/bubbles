import { Action, createReducer, on } from '@ngrx/store';
import { OIDCProvider } from './oidc-models';
import { addProviders, defineProvider, defineUser, setAccessToken, addResources, rememberRouteBeforeLogin, setRefreshToken, clearAccessToken } from './oidc-actions';

export const oidcFeatureKey = 'oidcState';
const storagePrefix = 'oidc_';

export interface Token {
  token: string; // token content
  expiresIn: number // token expire time
}

export interface OIDCState {
  currentOIDCProvider: string; // local ref
  currentUser: string; // global ref
  accessToken: Token; // token to make requests
  refreshToken: Token; // token to get new access token
  resources: Set<string>;
  allOIDCProviders : OIDCProvider[];
  route: string | undefined;
}

const initialState : OIDCState = getInitialState();
function getStorageItem(key: string): string|null {
  return sessionStorage.getItem(storagePrefix + key);
}
function saveStorageItem(key: string, value: string): void {
  sessionStorage.setItem(storagePrefix + key, value);
}

function getInitialState() : OIDCState {
  let accessExpires : number = 0;
  if (getStorageItem("token_access_expires") !== undefined)
  {
    accessExpires = Number.parseInt(getStorageItem("token_access_expires") || "0");
  }
  let refreshExpires : number = 0;
  if (getStorageItem("token_refresh_expires") !== undefined)
  {
    refreshExpires = Number.parseInt(getStorageItem("token_refresh_expires") || "0");
  }
  return {
    currentOIDCProvider : getStorageItem("provider") || '',
    currentUser : getStorageItem("user") || '',
    accessToken : {
      token : getStorageItem("token_access") || '',
      expiresIn : accessExpires
    },
    refreshToken : {
      token : getStorageItem("token_refresh") || '',
      expiresIn : refreshExpires
    },
    resources : new Set<string>(),
    allOIDCProviders : [],
    route : getStorageItem("route") || undefined
  };
}


const _oidcReducer = createReducer(
  initialState,
  on(addProviders, (state, action) => _addProviders(state, action.providers)),
  on(defineProvider, (state, action) => _defineProvider(state, action.provider)),
  on(defineUser, (state, action) => _defineUser(state, action.user)),
  on(setAccessToken, (state, action) => _setAccessToken(state, action.token, action.expires_in)),
  on(clearAccessToken, (state) => _clearAccessToken(state)),
  on(setRefreshToken, (state, action) => _setRefreshToken(state, action.token, action.expires_in)),
  on(addResources, (state, action) => _addResource(state, action.resources)),
  on(rememberRouteBeforeLogin, (state, action) => _rememberRouteBeforeLogin(state, action.url)),
);

export function oidcReducer(state: OIDCState | undefined, action: Action) {
  return _oidcReducer(state, action);
}

function _addProviders(state:OIDCState, providers:OIDCProvider[]):OIDCState
{
  return ({
    ...state,
    allOIDCProviders: providers
  });
}

function _defineProvider(state:OIDCState, provider:OIDCProvider):OIDCState
{
  saveStorageItem("provider", provider.localRef)
  return ({
    ...state,
    currentOIDCProvider: provider.localRef
  });
}

function _defineUser(state:OIDCState, user:string):OIDCState
{
  saveStorageItem("user", user)
  return ({
    ...state,
    currentUser: user
  });
}

function _setAccessToken(state:OIDCState, token:string, expires_in: number):OIDCState
{
  saveStorageItem("token_access", token);
  saveStorageItem("token_access_expires", expires_in.toString());
  let accessToken : Token = {token: token, expiresIn: expires_in};
  return ({
    ...state,
    accessToken: accessToken,
  });
}

function _clearAccessToken(state:OIDCState):OIDCState
{
  return ({
    ...state,
    accessToken: { token : "", expiresIn : 0},
  });
}

function _setRefreshToken(state:OIDCState, token:string, expires_in: number):OIDCState
{
  saveStorageItem("token_refresh", token);
  saveStorageItem("token_refresh_expires", expires_in.toString());
  let refreshToken : Token = {token: token, expiresIn: expires_in};
  return ({
    ...state,
    refreshToken: refreshToken,
  });
}

function _addResource(state:OIDCState, newResources:string[]):OIDCState
{
  var resources = new Set<string>(state.resources);
  newResources.forEach(r => resources.add(r));
  return ({
    ...state,
    resources: resources
  });
}

function _rememberRouteBeforeLogin(state:OIDCState, url:string):OIDCState
{
  saveStorageItem("route", url);
  return ({
    ...state,
    route: url,
  });
}
