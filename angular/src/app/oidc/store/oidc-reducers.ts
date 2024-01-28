import { Action, createReducer, on } from '@ngrx/store';
import { OIDCProvider, Token } from './oidc-models';
import { addProviders, defineProvider, defineUser, setAccessToken, addResources, rememberRouteBeforeLogin, setRefreshToken } from './oidc-actions';

export const oidcFeatureKey = 'oidcState';
const storagePrefix = 'oidc_';

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
  let identityExpires : number = 0;
  if (getStorageItem("token_id_expires") !== undefined)
  {
    identityExpires = Number.parseInt(getStorageItem("token_id_expires") || "0");
  }
  let resources = new Set<string>();
  if (getStorageItem("token_resources") !== undefined)
  {
    getStorageItem("token_resources")?.split('|').forEach(url => resources.add(url));
  }
  return {
    currentOIDCProvider : getStorageItem("provider") || '',
    currentUser : getStorageItem("user") || '',
    accessToken : {
      token : getStorageItem("token_access") || '',
      expires_in : accessExpires
    },
    refreshToken : {
      token : getStorageItem("token_refresh") || '',
      expires_in : refreshExpires
    },
    resources : resources,
    allOIDCProviders : [],
    route : getStorageItem("route") || undefined
  };
}


const _oidcReducer = createReducer(
  initialState,
  on(addProviders, (state, action) => _addProviders(state, action.providers)),
  on(defineProvider, (state, action) => _defineProvider(state, action.provider)),
  on(defineUser, (state, action) => _defineUser(state, action.user)),
  on(setAccessToken, (state, action) => _setAccessToken(state, action)),
  on(setRefreshToken, (state, action) => _setRefreshToken(state, action)),
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

function _setAccessToken(state:OIDCState, token:Token):OIDCState
{
  saveStorageItem("token_access", token.token);
  saveStorageItem("token_access_expires", token.expires_in.toString());
  return ({
    ...state,
    accessToken: token,
  });
}

function _setRefreshToken(state:OIDCState, token:Token):OIDCState
{
  saveStorageItem("token_refresh", token.token);
  saveStorageItem("token_refresh_expires", token.expires_in.toString());
  return ({
    ...state,
    refreshToken: token,
  });
}

function _addResource(state:OIDCState, newResources:string[]):OIDCState
{
  saveStorageItem("token_resources", newResources.join('|'));
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
