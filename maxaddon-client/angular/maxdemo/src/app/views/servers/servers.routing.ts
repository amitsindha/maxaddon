import { Routes } from '@angular/router';

import { AppServersComponent } from './app-servers/app-servers.component';


export const ServersRoutes: Routes = [
  {
    path: '',
    children: [{
      path: '',
      component: AppServersComponent,
      data: { title: 'Servers', breadcrumb: 'Servers' }
    }] 
  }
]; 