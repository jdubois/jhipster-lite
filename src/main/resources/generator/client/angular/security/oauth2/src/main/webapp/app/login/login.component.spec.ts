import { ComponentFixture, TestBed } from '@angular/core/testing';

import { By } from '@angular/platform-browser';
import { LoginComponent } from './login.component';
import { Oauth2AuthService } from '../auth/oauth2-auth.service';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;

  let oauth2AuthService: Oauth2AuthService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LoginComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;

    oauth2AuthService = TestBed.inject(Oauth2AuthService);

    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should logout on click on logout button', () => {
    jest.spyOn(oauth2AuthService, 'logout').mockImplementation(() => {});

    fixture.debugElement.query(By.css('#btn-logout')).nativeElement.click();

    expect(oauth2AuthService.logout).toHaveBeenCalledWith();
  });
});
