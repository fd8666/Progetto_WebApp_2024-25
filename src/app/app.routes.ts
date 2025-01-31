import { Routes } from '@angular/router';
import {FrontPageComponent} from './front-page/front-page.component';
import {LoginComponent} from './login/login.component';

export const routes: Routes = [
  {path:'',component:FrontPageComponent},
  {path:'Login',component:LoginComponent}
];
