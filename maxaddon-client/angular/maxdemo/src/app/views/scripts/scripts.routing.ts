import { Routes } from '@angular/router';

import { AppScriptsComponent } from './app-scripts/app-scripts.component';


export const ScriptsRoutes: Routes = [
  {
    path: '',
    children: [{
      path: '',
      component: AppScriptsComponent,
      data: { title: 'Scripts', breadcrumb: 'Scripts' }
    }] 
  }
]; 