import { Component, OnInit } from '@angular/core';
import {NgForOf} from '@angular/common';
import {RouterLink} from '@angular/router';
import {HeaderComponent} from '../header/header.component';
import {FooterComponent} from "../footer/footer.component";

@Component({
  selector: 'app-scambio',
  templateUrl: './scambio.component.html',
  styleUrls: ['./scambio.component.css'],
    imports: [
        NgForOf,
        RouterLink,
        HeaderComponent,
        FooterComponent
    ],
  standalone: true
})
export class ScambioComponent implements OnInit {

  carteProposte = [
    { id: 1, nome: 'Galarian-Zapdos-V', immagine: 'https://den-cards.pokellector.com/320/Galarian-Zapdos-V.CRE.174.38906.png' },
    { id: 2, nome: 'Chien-Pao-ex', immagine: 'https://den-cards.pokellector.com/367/Chien-Pao-ex.PAL.261.47792.png' },
  ];

  carteOfferte = [
    { id: 1, nome: 'Pikachu.SV2A', immagine: 'https://den-cards.pokellector.com/371/Pikachu.SV2A.173.48341.png' },
  ];

  messaggioScambio = 'Messaggio di scambio';

  constructor() { }

  ngOnInit(): void {
    // Qui gestiamo la logica per le carte e il messaggio
    this.visualizzaCarte();
  }

  visualizzaCarte(): void {
    // In Angular, si usano i binding per visualizzare i dati, quindi non è necessario manipolare il DOM direttamente
    // Aggiungiamo direttamente le carte nel template usando *ngFor
  }

  accettaScambio(): void {
    console.log('Scambio accettato');
  }

  rifiutaScambio(): void {
    console.log('Scambio rifiutato');
  }
}
