import { Routes } from '@angular/router';
import {FrontPageComponent} from './front-page/front-page.component';
import {RegistrazioneComponent} from './registrazione/registrazione.component';
import {LoginComponent} from './login/login.component';
import {ProfiloComponent} from './profilo/profilo.component';
import {InfoCartaComponent} from './info-carta/info-carta.component';
import {ContactComponent} from './contact/contact.component';

export const routes: Routes = [
  {path:'',component:FrontPageComponent},
  {path:'Registrazione', component:RegistrazioneComponent},
  {path:'Login',component:LoginComponent},
  {path:'profilo',component: ProfiloComponent},
  {path:'info-carta',component:InfoCartaComponent},
  {path:'contatti',component:ContactComponent}
];
