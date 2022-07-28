import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { LoginService } from './login.service';
import { AuthServerProvider } from '../auth/auth-jwt.service';
import { of, Subject } from 'rxjs';
import { AccountService } from '../auth/account.service';

describe('Login Service', () => {
  let service: LoginService;
  let mockAccountService: AccountService;
  let authServerProvider: AuthServerProvider;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [AccountService],
    });

    service = TestBed.inject(LoginService);
    mockAccountService = TestBed.inject(AccountService);
    authServerProvider = TestBed.inject(AuthServerProvider);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  describe('login', () => {
    it('should login', () => {
      // GIVEN
      mockAccountService.identity = jest.fn(() => of(null));
      const login = new Subject<void>();
      authServerProvider.login = jest.fn(() => login.asObservable());

      const credentials = {
        username: 'admin',
        password: 'admin',
      };

      // WHEN
      service.login(credentials).subscribe();
      login.next();

      // THEN
      expect(authServerProvider.login).toHaveBeenCalledWith(credentials);
      expect(mockAccountService.identity).toHaveBeenCalled();
    });
  });

  describe('logout', () => {
    it('should logout', () => {
      // GIVEN
      authServerProvider.logout = jest.fn(() => of());

      // WHEN
      service.logout();

      // THEN
      expect(authServerProvider.logout).toHaveBeenCalled();
    });
  });
});
