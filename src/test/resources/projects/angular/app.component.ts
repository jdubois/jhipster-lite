import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatMenuModule } from '@angular/material/menu';

@Component({
  selector: 'jhi-root',
  templateUrl: './app.component.html',
  imports: [CommonModule, RouterModule, MatMenuModule, MatToolbarModule, MatIconModule, MatButtonModule, MatButtonToggleModule],
  standalone: true,
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit{
  appName = '';

  ngOnInit(): void {
    this.appName = '{{baseName}}';
  }
}
