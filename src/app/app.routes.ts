import { Routes } from '@angular/router';
import {FrontPageComponent} from './front-page/front-page.component';
import {RegistrazioneComponent} from './registrazione/registrazione.component';
import {LoginComponent} from './login/login.component';
import {ContactComponent} from './contact/contact.component';
import {VetrinaComponent} from './vetrina/vetrina.component';
import {ScambioComponent} from './scambio/scambio.component';
import {PropostaScambioComponent} from './proposta-scambio/proposta-scambio.component';
import {EventiComponent} from './eventi/eventi.component';
import {ProfilePageComponent} from './profile-page/profile-page.component';
import {CardAnnouncementComponent} from './card-announcement/card-announcement.component';

export const routes: Routes = [
  {path:'',component:FrontPageComponent},
  {path:'Registrazione', component:RegistrazioneComponent},
  {path:'Login',component:LoginComponent},
  {path:'profilo',component: ProfilePageComponent},
  {path:'info-carta',component:CardAnnouncementComponent},
  {path:'contatti',component:ContactComponent},
  {path:'vetrina',component:VetrinaComponent},
  {path:'scambio',component:ScambioComponent},
  {path:'propostaScambio',component:PropostaScambioComponent},
  {path:'eventi',component:EventiComponent}

];
