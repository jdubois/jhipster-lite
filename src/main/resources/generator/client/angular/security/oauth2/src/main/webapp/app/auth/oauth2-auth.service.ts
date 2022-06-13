import { Injectable } from '@angular/core';
import Keycloak, { KeycloakConfig, KeycloakInstance } from 'keycloak-js';
import { from, Observable, tap } from 'rxjs';
import { environment } from '../../environments/environment';

const MIN_TOKEN_VALIDITY_SECONDS = 70;
const REFRESH_TOKEN_TIMEOUT_MS = 6000;

@Injectable({ providedIn: 'root' })
export class Oauth2AuthService {
  private keycloak!: KeycloakInstance;

  get token(): string | undefined {
    return this.keycloak.token;
  }

  get isAuthenticated(): boolean | undefined {
    return this.keycloak.authenticated;
  }

  initAuthentication(): Observable<boolean> {
    const config: KeycloakConfig = {
      url: environment.keycloak.url,
      realm: environment.keycloak.realm,
      clientId: environment.keycloak.client_id,
    };
    this.keycloak = Keycloak(config);

    return from(this.keycloak.init({ onLoad: 'login-required', checkLoginIframe: false })).pipe(
      tap(authenticated => {
        if (!authenticated) {
          window.location.reload();
        } else {
          console.debug('Authenticated');
        }
      }),
      tap(() => {
        this.initUpdateTokenRefresh();
      })
    );
  }

  logout(): void {
    this.keycloak.logout();
  }

  private initUpdateTokenRefresh(): void {
    setInterval(
      () =>
        this.keycloak
          .updateToken(MIN_TOKEN_VALIDITY_SECONDS)
          .then(refreshed => {
            if (refreshed) {
              console.debug('Token refreshed');
            } else {
              const exp = this.keycloak.tokenParsed!.exp!;
              const timeSkew = this.keycloak.timeSkew!;
              console.debug('Token not refreshed, valid for ' + Math.round(exp + timeSkew - Date.now() / 1000) + ' seconds');
            }
          })
          .catch(e => console.error('Failed to refresh token: ' + e)),
      REFRESH_TOKEN_TIMEOUT_MS
    );
  }
}
