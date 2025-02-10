import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {VetrinaComponent} from './vetrina/vetrina.component';
import {FooterComponent} from './footer/footer.component';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, FooterComponent],
  templateUrl: './app.component.html',
  standalone: true,
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'cardswap-frontend';
}
