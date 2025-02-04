import { Component, OnInit, Renderer2 } from '@angular/core';
import {RouterLink} from '@angular/router';
import {HeaderComponent} from '../header/header.component';

@Component({
  selector: 'app-front-page',
  templateUrl: './front-page.component.html',
  styleUrls: ['./front-page.component.css'],
  imports: [
    RouterLink,
    HeaderComponent
  ],
  standalone: true
})
export class FrontPageComponent{


}
