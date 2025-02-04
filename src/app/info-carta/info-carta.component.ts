import { Component } from '@angular/core';
import { HeaderComponent} from '../header/header.component'; // Importa il componente Header


@Component({
  selector: 'app-info-carta',
  imports: [
    HeaderComponent
  ],
  templateUrl: './info-carta.component.html',
  standalone: true,
  styleUrl: './info-carta.component.css'
})
export class InfoCartaComponent {

}
