import { Store } from '@ngrx/store';
import { Injectable } from '@angular/core';

import { OIDCAuthenticationService } from './oidc-authentication';
import { OIDCAuthorizationService } from './oidc-authorization';
import { OIDCService } from './oidc.service';
import { OIDCState, OIDCProvider, addProviders, defineUser, setAccessToken, addResources, setRefreshToken, NULL_TOKEN, selectProviders, selectCurrentProvider, Token, selectRefreshToken, selectAccessToken } from 'src/app/oidc/store';
import { environment } from 'src/environments/environment';
import { ServiceEndpoint, ServiceState, clearClientErrors, selectClientError, updateServiceLocation } from 'src/app/core/service';
import * as oauth2 from 'angular-oauth2-oidc';

export interface TokenResponse extends oauth2.TokenResponse {
  refresh_expires_in: number;
}

@Injectable({ providedIn: 'root' })
export class OIDCFacade {
  allOIDCProviders: OIDCProvider[] = [];
  oidc_provider: OIDCProvider | undefined = undefined;
  refresh_token : Token | undefined = undefined;
  access_token : Token | undefined = undefined;
  get_refesh_trigger : boolean = false;

  constructor(
    private readonly store: Store<OIDCState>,
    private readonly service : OIDCService,
    private readonly serviceStore: Store<ServiceState>,
    private readonly authorizationService : OIDCAuthorizationService,
    private readonly authenticationService : OIDCAuthenticationService,
    ) {
      this.store.select(selectProviders).subscribe(allProviders => {
        this.allOIDCProviders = allProviders;
        if (allProviders.length == 0)
        {
          console.log("facade oidc providers received");
          this.tryToGetAccessToken();
        }
      });
      this.store.select(selectCurrentProvider).subscribe( p => {
        if (p !== undefined)
        {
          console.log("facade oidc provider received");
          this.oidc_provider = p;
          this.tryToGetAccessToken();
        }
      });
      this.store.select(selectRefreshToken).subscribe( t => {
        if (t !== undefined)
        {
          this.refresh_token = t;
          console.log("facade refresh token received", this.refresh_token, new Date(t.expires_in).toUTCString());
          this.tryToGetAccessToken();
        }
      });
      this.store.select(selectAccessToken).subscribe( t => {
        if (t !== undefined)
        {
          this.access_token = t;
          console.log("facade accesss token received", this.refresh_token, new Date(t.expires_in).toUTCString());
          this.tryToGetAccessToken();
        }
      });
      serviceStore.select(selectClientError).subscribe(errors => {
        if (errors.length > 0)
        {
          let error = errors[0];
          if (error.status == 401)
          {
            // handle the error -> removing from list
            serviceStore.dispatch(clearClientErrors());
            // unauthorized could mean access token is invalid
            this.tryToGetAccessToken();
          }
        }
      });
  }
  loadIdentityProviders() {
    this.serviceStore.dispatch(updateServiceLocation(
      { serviceEndpoint : new ServiceEndpoint().init(environment.serviceEndPoint)}));
    this.service.loadIdentityProviders()
      .subscribe(providers => this.store.dispatch(addProviders({ providers : providers})));
  }
  loginViaCodeFlow(p : OIDCProvider | undefined) {
    if (p !== undefined) {
      this.authenticationService.getTokenForCodeFlow(p).then((idToken) =>
      {
        this.authorizationService.getAccessToken(p, idToken).then((tokenResponse) => {
          this.storeTokens(tokenResponse);
          // response.scope will contain list of URIs assigned to that token
          this.store.dispatch(addResources({ resources : this.getScopes(tokenResponse.scope)}));
          var claims = this.getClaims(tokenResponse.access_token);
          this.store.dispatch(defineUser({ user : this.getUser(claims)}));
        });
      });
    }
  }

  private getAccessWithRefreshToken() : void {
    console.log("facade - try to get access token with refresh token");
    if (this.oidc_provider !== undefined && this.refresh_token !== undefined && this.refresh_token.expires_in > 0 && !this.get_refesh_trigger)
    {
      this.get_refesh_trigger = true;
      this.authorizationService.getAccessTokenWithRefreshToken(this.oidc_provider, this.refresh_token.token)
      .then(r => {
        this.storeTokens(r);
        this.get_refesh_trigger = false;
      }).catch(error => {
        this.get_refesh_trigger = false;
      });
    }
    else {
      console.log("required data not available to retrieve access token");
      if (this.oidc_provider !== undefined)
      {
        console.log("oidc provider is not defined yet");
      }
      if (this.allOIDCProviders.length == 0)
      {
        console.log("list of oidc provider not provided yet");
      }
    }
  }

  private storeTokens(tokenResponse: TokenResponse):void {
    // access token will stored
    this.store.dispatch(setAccessToken({
      token : tokenResponse.access_token,
      expires_in: tokenResponse.expires_in
    }));
    // refresh token will stored
    this.store.dispatch(setRefreshToken({
      token : tokenResponse.refresh_token,
      expires_in: tokenResponse.refresh_expires_in
    }));
  }

  private getClaims(access_token:string):any {
    const tokenParts = access_token.split('.');
    const claimsBase64 = this.padBase64(tokenParts[1]);
    const claimsJson = this.b64DecodeUnicode(claimsBase64);
    const claims = JSON.parse(claimsJson);
    // console.log("claims from access_token", claims);
    return claims;
  }
  private getScopes(scopes : string):string[] {
    return scopes.split(" ");
  }
  private getUser(claims:any):string {
    return claims.sub;
  }

  private padBase64(base64data:any): string {
    while (base64data.length % 4 !== 0) {
      base64data += '=';
    }
    return base64data;
  }

  // see: https://developer.mozilla.org/en-US/docs/Web/API/WindowBase64/Base64_encoding_and_decoding#The_.22Unicode_Problem.22
  private b64DecodeUnicode(str:string) {
    const base64 = str.replace(/\-/g, '+').replace(/\_/g, '/');

    return decodeURIComponent(
      atob(base64)
        .split('')
        .map(function (c) {
          return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
        })
        .join('')
    );
  }

  public clearAccessToken():void {
    if (this.access_token !== undefined && this.access_token.expires_in != 0 && this.access_token.expires_in < new Date().getTime())
    {
      console.log("clear access token", new Date(this.access_token.expires_in).toUTCString());
      this.access_token = undefined;
      this.store.dispatch(setAccessToken(NULL_TOKEN));
      this.tryToGetAccessToken();
    }
  }
  public clearRefreshToken():void {
    console.log("clear refresh token");
    this.store.dispatch(setRefreshToken(NULL_TOKEN));
  }


  public tryToGetAccessToken(): void
  {
    if (this.allOIDCProviders.length == 0)
    {
      this.loadIdentityProviders();
      return;
    }
    if (this.oidc_provider === undefined)
    {
      console.log("oidc provider still undefined");
      return;
    }
    let now = new Date();
    console.log("now", now.toUTCString());
    if (this.access_token !== undefined && this.access_token.expires_in != 0 && this.access_token.expires_in < now.getTime())
    {
      this.clearAccessToken();
    }
    if (this.refresh_token !== undefined && this.refresh_token.expires_in != 0 && this.refresh_token.expires_in < now.getTime())
    {
      this.clearRefreshToken();
    }
    if ((this.access_token === undefined || this.access_token?.expires_in == 0) && (this.refresh_token !== undefined && this.refresh_token?.expires_in > 0))
    {
      this.getAccessWithRefreshToken();
    }
  }
}
