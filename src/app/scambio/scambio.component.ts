import { Component, OnInit } from '@angular/core';
import {NgForOf} from '@angular/common';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-scambio',
  templateUrl: './scambio.component.html',
  styleUrls: ['./scambio.component.css'],
  imports: [
    NgForOf,
    RouterLink
  ],
  standalone: true
})
export class ScambioComponent implements OnInit {

  carteProposte = [
    { id: 1, nome: 'Carta 1', immagine: 'https://via.placeholder.com/80x120' },
    { id: 2, nome: 'Carta 2', immagine: 'https://via.placeholder.com/80x120' },
    { id: 3, nome: 'Carta 3', immagine: 'https://via.placeholder.com/80x120' },
  ];

  carteOfferte = [
    { id: 1, nome: 'Carta offerta 1', immagine: 'https://via.placeholder.com/80x120' },
    { id: 2, nome: 'Carta offerta 2', immagine: 'https://via.placeholder.com/80x120' },
    { id: 3, nome: 'Carta offerta 3', immagine: 'https://via.placeholder.com/80x120' },
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
