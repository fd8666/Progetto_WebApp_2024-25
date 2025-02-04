import { Component } from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule} from '@angular/router';
import {HeaderComponent} from '../header/header.component';

@Component({
  selector: 'app-registrazione',
  imports: [CommonModule, RouterModule, HeaderComponent],
  templateUrl: './registrazione.component.html',
  standalone: true,
  styleUrl: './registrazione.component.css'
})
export class RegistrazioneComponent {}
