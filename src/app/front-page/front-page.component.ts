import { Component } from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule} from '@angular/router';

@Component({
  selector: 'app-front-page',
  imports: [CommonModule,RouterModule],
  templateUrl: './front-page.component.html',
  standalone: true,
  styleUrl: './front-page.component.css'

})
export class FrontPageComponent {}
