import { Component } from '@angular/core';
import { HeaderComponent } from '../header/header.component'; // Importa il componente Header

@Component({
  selector: 'app-profilo',
  standalone: true,
  imports: [
    HeaderComponent // Aggiunto per risolvere l'errore
  ],
  templateUrl: './profilo.component.html',
  styleUrls: ['./profilo.component.css']
})
export class ProfiloComponent {

}

