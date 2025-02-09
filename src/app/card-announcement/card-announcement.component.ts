import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { HeaderComponent } from '../header/header.component';
import {CurrencyPipe, NgIf} from '@angular/common';
import {FooterComponent} from "../footer/footer.component";
import {PropostaScambioComponent} from '../proposta-scambio/proposta-scambio.component';

@Component({
  selector: 'app-dettaglio',
  templateUrl: './card-announcement.component.html',
  standalone: true,
  imports: [
    HeaderComponent,
    NgIf,
    RouterLink,
    CurrencyPipe,
    FooterComponent,
    PropostaScambioComponent
  ],
  styleUrls: ['./card-announcement.component.css']
})
export class CardAnnouncementComponent implements OnInit {
  annuncio: any = null;
  loading: boolean = true;
  error: boolean = false;
  isScambioVisible:boolean=false;
  private apiUrl = 'http://localhost:8080/api/cards/getCard'; // URL API backend

  constructor(private route: ActivatedRoute, private http: HttpClient) {}

  ngOnInit() {
    const annuncioId = Number(this.route.snapshot.paramMap.get('id'));
    if (!annuncioId) {
      console.error("ID annuncio non valido");
      this.error = true;
      this.loading = false;
      return;
    }

    // Effettua la chiamata al backend per ottenere i dettagli dell'annuncio
    this.http.get(`${this.apiUrl}/${annuncioId}`).subscribe({
      next: (response: any) => {
        console.log('Risposta ricevuta:', response);

        // Estrai il body (stringa) dalla risposta
        const bodyString = response.body;

        // Puliscie e riformatta la stringa per renderla un JSON valido
        try {
          // Rimuovi eventuali caratteri indesiderati prima di "CardEntryPostgres"
          let jsonString = bodyString
            .replace(/^CardEntryPostgres\[/, '{')
            .replace(/\]$/, '}')
            .replace(/=/g, '":')
            .replace(/([a-zA-Z0-9_]+)":/g, '"$1":');

          // Aggiungi virgolette ai valori stringa che non le hanno
          jsonString = jsonString.replace(/"([a-zA-Z0-9_]+)":([^",}]+)/g, '"$1":"$2"');

          // Logging della stringa JSON formattata
          console.log("Stringa JSON corretta:", jsonString);

          // Controlla la validità della stringa JSON prima del parsing
          if (jsonString && jsonString[0] === '{' && jsonString[jsonString.length - 1] === '}') {
            // Parsing della stringa in un oggetto JSON
            this.annuncio = JSON.parse(jsonString);
            this.loading = false;
          } else {
            throw new Error('La stringa JSON è malformata');
          }

        } catch (error) {
          console.error("Errore nel parsing dei dati:", error);
          this.error = true;
          this.loading = false;
        }
      },
      error: (err) => {
        console.error("Errore nel recupero dell'annuncio:", err);
        this.error = true;
        this.loading = false;
      }
    });
  }

  proponiScambio() {
    console.log('Proposta di scambio per:', this.annuncio);
    this.isScambioVisible=true;
  }
}
