import { Oauth2AuthService } from './oauth2-auth.service';
import { TestBed } from '@angular/core/testing';
import Keycloak, { KeycloakError, KeycloakInitOptions, KeycloakInstance, KeycloakPromise } from 'keycloak-js';
import { lastValueFrom } from 'rxjs';
import SpyInstance = jest.SpyInstance;

jest.mock('keycloak-js', () => ({
  __esModule: true,
  default: jest.fn().mockReturnValue({
    init: jest.fn().mockReturnValue(Promise.resolve(true) as unknown as KeycloakPromise<boolean, any>),
    updateToken: jest.fn().mockReturnValue(Promise.resolve(true) as unknown as KeycloakPromise<boolean, any>),
    logout: jest.fn().mockReturnValue(Promise.resolve(null) as unknown as KeycloakPromise<void, void>),
    idToken: 'idTokenValue',
    token: 'tokenValue',
    tokenParsed: {
      exp: 1200,
    },
    timeSkew: 0,
  } as unknown as KeycloakInstance),
}));

jest.mock('../../environments/environment', () => ({
  environment: {
    production: false,
    keycloak: {
      url: 'http://localhost:1234',
      realm: 'jhipster',
      client_id: 'web_app',
    },
  },
}));

const SERVER_URL = 'http://localhost:1234';
const REALM = 'jhipster';
const CLIENT_ID = 'web_app';
const TOKEN = '1a2b3c';
const UPDATE_TOKEN_INTERVAL_MS = 6000;

describe('Oauth2 Auth Service', () => {
  let service: Oauth2AuthService;
  let keycloakInstance: KeycloakInstance;

  let consoleDebugMock: SpyInstance;
  let consoleErrorMock: SpyInstance;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [],
    });

    service = TestBed.inject(Oauth2AuthService);
    keycloakInstance = Keycloak();

    consoleDebugMock = jest.spyOn(console, 'debug').mockImplementation(() => {});
    consoleErrorMock = jest.spyOn(console, 'error').mockImplementation(() => {});
  });

  afterEach(() => {
    consoleDebugMock.mockRestore();
    consoleErrorMock.mockRestore();
  });

  describe('init authentication', () => {
    beforeEach(() => {
      Object.defineProperty(window, 'location', {
        value: {
          reload: jest.fn(),
        },
      });
    });

    it('should init refresh token and return true when the user is authenticated', async () => {
      // Given
      jest
        .spyOn(keycloakInstance, 'init')
        .mockReturnValue(Promise.resolve(true).then() as unknown as KeycloakPromise<boolean, KeycloakError>);

      // When
      const authenticated = await lastValueFrom(service.initAuthentication());

      // Then
      expect(authenticated).toBeTruthy();
      expect(Keycloak).toHaveBeenCalledWith({
        url: SERVER_URL,
        realm: REALM,
        clientId: CLIENT_ID,
      });
      const expectedInitParams: KeycloakInitOptions = { onLoad: 'login-required', checkLoginIframe: false };
      expect(keycloakInstance.init).toHaveBeenCalledWith(expectedInitParams);
      expect(window.location.reload).not.toHaveBeenCalled();
      expect(console.debug).toHaveBeenCalledWith('Authenticated');
    });

    it('should reload window and return false when the user is not authenticated', async () => {
      // Given
      jest
        .spyOn(keycloakInstance, 'init')
        .mockReturnValue(Promise.resolve(false).then() as unknown as KeycloakPromise<boolean, KeycloakError>);

      // When
      const authenticated = await lastValueFrom(service.initAuthentication());

      // Then
      expect(authenticated).toBeFalsy();
      expect(Keycloak).toHaveBeenCalledWith({
        url: SERVER_URL,
        realm: REALM,
        clientId: CLIENT_ID,
      });
      const expectedInitParams: KeycloakInitOptions = { onLoad: 'login-required', checkLoginIframe: false };
      expect(keycloakInstance.init).toHaveBeenCalledWith(expectedInitParams);
      expect(window.location.reload).toHaveBeenCalled();
    });
  });

  describe('Update token', () => {
    beforeEach(() => {
      jest.useFakeTimers();
    });

    it('should call update token and log debug validity time message when token is valid', async () => {
      // Given

      keycloakInstance.tokenParsed = {
        exp: 1651319001, // 2022-04-30 13:43:21
      };
      keycloakInstance.timeSkew = 3;
      jest.spyOn(Date, 'now').mockReturnValue(1651318847714); // 2022-04-30 13:40:47

      let updateTokenPromise = Promise.resolve(false);
      jest.spyOn(keycloakInstance, 'updateToken').mockReturnValue(updateTokenPromise as unknown as KeycloakPromise<boolean, boolean>);

      // When
      await lastValueFrom(service.initAuthentication());

      jest.advanceTimersByTime(UPDATE_TOKEN_INTERVAL_MS);
      await updateTokenPromise;

      // Then
      expect(keycloakInstance.updateToken).toHaveBeenCalledWith(70);
      expect(console.debug).toHaveBeenCalledWith('Token not refreshed, valid for 156 seconds');
    });

    it('should call update token and log debug "refreshed" message when token is refreshed', async () => {
      // Given
      let updateTokenPromise = Promise.resolve(true);
      jest.spyOn(keycloakInstance, 'updateToken').mockReturnValue(updateTokenPromise as unknown as KeycloakPromise<boolean, boolean>);

      // When
      await lastValueFrom(service.initAuthentication());

      jest.advanceTimersByTime(UPDATE_TOKEN_INTERVAL_MS);
      await updateTokenPromise;

      // Then
      expect(keycloakInstance.updateToken).toHaveBeenCalledWith(70);
      expect(console.debug).toHaveBeenCalledWith('Token refreshed');
    });

    it('should call update token and log error message on error', async () => {
      // Given
      let updateTokenPromise = Promise.reject(new Error('unknown error'));
      jest.spyOn(keycloakInstance, 'updateToken').mockReturnValue(updateTokenPromise as unknown as KeycloakPromise<boolean, boolean>);

      // When
      await lastValueFrom(service.initAuthentication());

      jest.advanceTimersByTime(UPDATE_TOKEN_INTERVAL_MS);

      expect(keycloakInstance.updateToken).toHaveBeenCalledWith(70);

      await expect(updateTokenPromise).rejects.toEqual(new Error('unknown error'));
      expect(console.error).toHaveBeenCalledWith('Failed to refresh token: Error: unknown error');
    });
  });

  describe('Logout', () => {
    it('should logout', () => {
      // Given
      service.initAuthentication();

      // When
      service.logout();

      // Then
      expect(keycloakInstance.logout).toHaveBeenCalledWith();
    });
  });

  describe('Get token', () => {
    it('should return token', () => {
      // Given
      service.initAuthentication();
      Object.defineProperty(keycloakInstance, 'token', {
        value: TOKEN,
      });

      // When + Then
      expect(service.token).toEqual(TOKEN);
    });
  });

  describe('Is Authenticated', () => {
    beforeEach(() => {
      service.initAuthentication();
    });

    it('should return true when the user is authenticated', () => {
      // Given
      Object.defineProperty(keycloakInstance, 'authenticated', {
        value: true,
      });

      // When + Then
      expect(service.token).toBeTruthy();
    });

    it('should return false when the user is authenticated', () => {
      // Given
      Object.defineProperty(keycloakInstance, 'authenticated', {
        value: false,
      });

      // When + Then
      expect(service.isAuthenticated).toBeFalsy();
    });
  });
});
