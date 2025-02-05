import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from '../header/header.component';
import { trigger, state, style, transition, animate } from '@angular/animations';
import {NgClass, NgFor} from '@angular/common';

@Component({
  selector: 'app-front-page',
  templateUrl: './front-page.component.html',
  styleUrls: ['./front-page.component.css'],
  imports: [
    RouterLink,
    HeaderComponent,
    NgFor,
    NgClass,
    CommonModule
  ],
  standalone: true,
  animations: [
    trigger('faqAnimation', [
      state('open', style({ height: '*', opacity: 1 })),
      state('closed', style({ height: '0px', opacity: 0, overflow: 'hidden' })),
      transition('closed <=> open', animate('300ms ease-in-out'))
    ])
  ]
})
export class FrontPageComponent {
  openedFaq: number | null = null;

  faqs = [
    { question: 'Come posso scambiare le mie carte?', answer: 'Per scambiare le tue carte, vai alla sezione Vetrina e trova altri collezionisti interessati.' },
    { question: 'Come posso vendere le mie carte?', answer: 'Per vendere le tue carte, vai alla sezione Vendite e segui le istruzioni per mettere in vendita le tue carte.' },
    { question: 'Come posso acquistare carte?', answer: 'Per acquistare carte, vai alla sezione Vetrina e scegli le carte che desideri acquistare.' },
    { question: 'Come posso partecipare agli eventi?', answer: 'Visita la sezione Eventi per scoprire gli eventi in programma vicino a te e ottenere maggiori dettagli su luoghi, date e partecipanti.' },
    { question: 'Come posso contattare il supporto?', answer: 'Visita la sezione Contatti per trovare i nostri recapiti e la nostra posizione. Puoi contattarci via email o telefono per qualsiasi necessità.' },
    { question: 'Come posso creare un account?', answer: "Per creare un account, clicca sull'icona dell'account in alto a destra e seleziona Registrati." }
  ];

  toggleFaq(index: number) {
    this.openedFaq = this.openedFaq === index ? null : index;
  }

  slideIndex: number = 0;
  slides: { src: string }[] = [
    { src: 'https://www.guizettefamily.com/wp-content/uploads/2024/08/Pokemon-JCC-Etincelles-Deferlantes-1.jpg' },
    { src: 'https://cardotaku.com/cdn/shop/collections/one-piece-card-game-booster-pack-op-09-emperors-in-the-new-world-cards-list.png?v=1720055194&width=1200' },
    { src: 'https://www.disneylorcana.com/cms/gallery/lorcana-web/landing/shimmeringskies_it_thumbnail.jpg' }
  ];

  ngOnInit() {
    this.showSlides();
  }

  showSlides() {
    setInterval(() => {
      this.slideIndex = (this.slideIndex + 1) % this.slides.length;
    }, 2000); // Cambia immagine ogni 2 secondi
  }


  currentSlide(index: number) {
    this.slideIndex = index;
  }
}
