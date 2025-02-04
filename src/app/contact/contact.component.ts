import { Component } from '@angular/core';
import {ContactMapComponent} from '../contact-map/contact-map.component';
import {RouterLink} from '@angular/router';
import {HeaderComponent} from '../header/header.component';

@Component({
  selector: 'app-contact',
  standalone: true,
  templateUrl: './contact.component.html',
  imports: [
    ContactMapComponent,
    RouterLink,
    HeaderComponent
  ],
  styleUrls: ['./contact.component.css']
})
export class ContactComponent {}
