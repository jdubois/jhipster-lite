import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AuthServerProvider } from './auth-jwt.service';

describe('Auth JWT', () => {
  let service: AuthServerProvider;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });

    httpMock = TestBed.inject(HttpTestingController);
    service = TestBed.inject(AuthServerProvider);
  });

  describe('Login', () => {
    it('should clear session storage and save in local storage', () => {
      // GIVEN
      jest.spyOn(Storage.prototype, 'setItem');

      // WHEN
      service.login({ username: 'John', password: '123' }).subscribe();
      httpMock.expectOne('api/authenticate').flush({ id_token: '1' });

      // THEN
      httpMock.verify();
      expect(localStorage.setItem).toHaveBeenCalledWith('authenticationToken', '1');
    });
  });

  describe('Logout', () => {
    it('should clear storage', () => {
      // GIVEN
      jest.spyOn(Storage.prototype, 'removeItem');

      // WHEN
      service.logout().subscribe();

      // THEN
      expect(localStorage.removeItem).toHaveBeenCalledWith('authenticationToken');
    });
  });
});
