import { TestBed } from '@angular/core/testing';
import { HttpHandler, HttpRequest } from '@angular/common/http';
import { lastValueFrom, of } from 'rxjs';

import { HttpAuthInterceptor } from './http-auth.interceptor';
import { Oauth2AuthService } from './oauth2-auth.service';

import Mock = jest.Mock;

const URL = 'http://localhost:8080/api/dummy';
const HTTP_METHOD = 'GET';
const TOKEN = '1a2b3c';

const buildHttpRequest = () => {
  let originalRequest: HttpRequest<unknown> = new HttpRequest<unknown>(HTTP_METHOD, URL);
  return originalRequest.clone({
    headers: originalRequest.headers.append('ContentType', 'application/json'),
  });
};

describe('HttpAuthInterceptor', () => {
  let interceptor: HttpAuthInterceptor;
  let oauth2AuthService: Oauth2AuthService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [HttpAuthInterceptor, Oauth2AuthService],
    });

    interceptor = TestBed.inject(HttpAuthInterceptor);
    oauth2AuthService = TestBed.inject(Oauth2AuthService);
  });

  it('should be created', () => {
    expect(interceptor).toBeTruthy();
  });

  describe('Bearer Token', () => {
    let httpHandler: HttpHandler;
    let httpHandlerNextMock: Mock;

    beforeEach(() => {
      httpHandlerNextMock = jest.fn().mockReturnValue(of(null));
      httpHandler = {
        handle: httpHandlerNextMock,
      } as unknown as HttpHandler;
    });

    it('should add authorization bearer token in request when defined', async () => {
      jest.spyOn(oauth2AuthService, 'token', 'get').mockReturnValue(TOKEN);
      let originalRequest = buildHttpRequest();

      await lastValueFrom(interceptor.intercept(originalRequest, httpHandler));

      expect(httpHandler.handle).toHaveBeenCalledTimes(1);
      const request: HttpRequest<unknown> = httpHandlerNextMock.mock.calls[0][0];
      expect(request.method).toEqual(HTTP_METHOD);
      expect(request.url).toEqual(URL);
      expect(request.headers.get('ContentType')).toEqual('application/json');
      expect(request.headers.get('Authorization')).toEqual(`Bearer ${TOKEN}`);
    });

    it('should not add authorization bearer token in request when it is not defined', async () => {
      jest.spyOn(oauth2AuthService, 'token', 'get').mockReturnValue(undefined);
      let originalRequest: HttpRequest<unknown> = buildHttpRequest();

      await lastValueFrom(interceptor.intercept(originalRequest, httpHandler));

      expect(httpHandler.handle).toHaveBeenCalledTimes(1);
      const request: HttpRequest<unknown> = httpHandlerNextMock.mock.calls[0][0];
      expect(request.headers.get('ContentType')).toEqual('application/json');
      expect(request.headers.get('Authorization')).toBeFalsy();
    });
  });
});
