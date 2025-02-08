import { Component } from '@angular/core';
import {RouterLink} from '@angular/router';
import {HeaderComponent} from '../header/header.component';
import {FooterComponent} from "../footer/footer.component";

@Component({
  selector: 'app-proposta-scambio',
    imports: [
        RouterLink,
        HeaderComponent,
        FooterComponent
    ],
  templateUrl: './proposta-scambio.component.html',
  standalone: true,
  styleUrl: './proposta-scambio.component.css'
})
export class PropostaScambioComponent {

}
