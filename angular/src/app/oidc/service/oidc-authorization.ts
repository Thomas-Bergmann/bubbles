import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';

import { OIDCProvider } from '../store';
import { OIDCFacade, TokenResponse } from './oidc.facade';
import { catchError, throwError } from 'rxjs';

/**
 * The authorization service will convert an identity token to an access_token for the resource service
 */
@Injectable({ providedIn: 'root' })
export class OIDCAuthorizationService {
  constructor(
    private http: HttpClient,
    private readonly oidcFacade: OIDCFacade,
  ) {}

  getAccessToken(provider:OIDCProvider, idToken: String):Promise<TokenResponse> {
    return new Promise((resolve, reject) => {
      const httpOptions = {
        headers : new HttpHeaders({
          'Authorization': 'Bearer ' + idToken,
        }),
      }
      var data : OAuthUserAuthenticationRO = {
        idp : provider.localRef,
        type : "id",
      };
      this.http
        .post<TokenResponse>(provider.authorizationURI, data, httpOptions)
        .pipe(
          catchError(error => {
            let errorMsg: string;
            if (error.error instanceof ErrorEvent) {
                errorMsg = error.error.message;
            } else if (error.error instanceof HttpErrorResponse) {
              errorMsg = error.message;
              if (error.status == 401 || error.status == 403)
              {
                console.log("identity token doesn't work");
              }
            } else {
              errorMsg = error.message;
            }
            return throwError(() => errorMsg);
          })
        )
        .subscribe((tokenResponse) => resolve(tokenResponse));
    });
  }
  getAccessTokenWithRefreshToken(provider:OIDCProvider, refreshToken: String):Promise<TokenResponse> {
    return new Promise((resolve, reject) => {
      const httpOptions = {
        headers : new HttpHeaders({
          'Authorization': 'Bearer ' + refreshToken,
        }),
      }
      var data : OAuthUserAuthenticationRO  = {
        idp : provider.localRef,
        type : "refresh"
      }
      console.log("try to get access token with refresh token");
      this.http
        .post<TokenResponse>(provider.authorizationURI, data, httpOptions)
        .pipe(
          catchError(error => {
            let errorMsg: string;
            if (error.error instanceof ErrorEvent) {
                errorMsg = error.error.message;
            } else if (error.error instanceof HttpErrorResponse) {
              errorMsg = error.message;
              if (error.status == 401 || error.status == 403)
              {
                console.log("refresh token doesn't work");
                this.oidcFacade.clearRefreshToken();
              }
            } else {
              errorMsg = error.message;
            }
            return throwError(() => errorMsg);
          })
        )
        .subscribe((tokenResponse) => resolve(tokenResponse));
    });
  }
}

 interface OAuthUserAuthenticationRO {
  idp : string,
  type: string,
 }
