import { Component } from '@angular/core';
import { Oauth2AuthService } from '../auth/oauth2-auth.service';

@Component({
  selector: 'jhi-login',
  templateUrl: './login.component.html',
  styleUrls: [],
})
export class LoginComponent {
  constructor(private oauth2AuthService: Oauth2AuthService) {}

  logout(): void {
    this.oauth2AuthService.logout();
  }
}
