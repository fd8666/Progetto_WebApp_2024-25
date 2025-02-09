import {Component, ElementRef, ViewChild} from '@angular/core';
import {NgClass, NgForOf, NgIf, NgStyle} from '@angular/common';

@Component({
  selector: 'app-proposta-scambio',
  standalone: true,
  templateUrl: './proposta-scambio.component.html',
  imports: [
    NgClass,
    NgForOf,
    NgIf,
    NgStyle
  ],
  styleUrls: ['./proposta-scambio.component.css']
})
export class PropostaScambioComponent {
  carteDisponibili = [
    { nome: 'Carta 1', immagine: 'https://den-cards.pokellector.com/371/Pikachu.SV2A.173.48341.png' },
    { nome: 'Carta 2', immagine: 'https://den-cards.pokellector.com/365/Lumineon-V.GG.GG39.46538.png' },
    { nome: 'Carta 3', immagine: 'https://via.placeholder.com/80x120' },
    { nome: 'Carta 4', immagine: 'https://via.placeholder.com/80x120' },
    { nome: 'Carta 5', immagine: 'https://via.placeholder.com/80x120' }
  ];

  carteSelezionate: any[] = [null, null, null, null, null]; // 5 slot vuoti
  listaVisibile = -1;  // Indice della carta per cui mostrare la lista, -1 significa nessuna lista visibile
  posizioneLista: { top: number, left: number } = { top: 0, left: 0 };

  @ViewChild('cartaSelezionata', { static: false }) cartaSelezionata: ElementRef | undefined;

  // Mostra o nascondi la lista delle carte sotto la carta selezionata
  toggleLista(index: number, event: MouseEvent) {
    if (this.listaVisibile === index) {
      // Se la lista è già visibile, la nascondi
      this.listaVisibile = -1;
    } else {
      // Altrimenti, mostra la lista sotto questa carta
      this.listaVisibile = index;

      // Calcola la posizione della lista sotto il + cliccato
      const rect = (event.target as HTMLElement).getBoundingClientRect();
      this.posizioneLista = {
        top: rect.bottom + window.scrollY,  // Posiziona la lista sotto l'elemento cliccato
        left: rect.left + window.scrollX    // Allinea la lista con l'elemento cliccato
      };
    }
  }

  selezionaCarta(carta: any) {
    if (this.listaVisibile !== -1) {
      // Aggiungi la carta selezionata all'indice corretto
      this.carteSelezionate[this.listaVisibile] = carta;
      // Nascondi la lista
      this.listaVisibile = -1;
    }
  }

  inviaProposta() {
    alert('Proposta di scambio inviata!');
    console.log('Carte selezionate:', this.carteSelezionate);
  }

  annullaProposta() {
    this.carteSelezionate = [null, null, null, null, null]; // Reset
    alert('Proposta annullata.');
  }
}
