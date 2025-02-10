import { Component } from '@angular/core';
import {HeaderComponent} from '../header/header.component';
import {FooterComponent} from "../footer/footer.component";

@Component({
  selector: 'app-eventi',
    imports: [
        HeaderComponent,
        FooterComponent
    ],
  templateUrl: './eventi.component.html',
  standalone: true,
  styleUrl: './eventi.component.css'
})
export class EventiComponent {

}
