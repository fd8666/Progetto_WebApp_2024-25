import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import {routes} from './app/app.routes';
import {provideRouter} from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import {AuthService} from './app/services/auth.service';


bootstrapApplication(AppComponent, {
  providers:[
    provideRouter(routes),
    provideHttpClient(), AuthService]
})
  .catch((err) => console.error(err));

