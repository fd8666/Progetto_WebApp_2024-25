import { Routes } from '@angular/router';
import {FrontPageComponent} from './front-page/front-page.component';
import {RegistrazioneComponent} from './registrazione/registrazione.component';
import {LoginComponent} from './login/login.component';

export const routes: Routes = [
  {path:'',component:FrontPageComponent},
  {path:'Registrazione', component:RegistrazioneComponent},
  {path:'Login',component:LoginComponent}
];
