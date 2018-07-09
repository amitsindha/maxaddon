import { Routes } from '@angular/router';

import { AppApplicationsComponent } from './app-applications/app-applications.component';


export const ApplicationsRoutes: Routes = [
  {
    path: '',
    children: [{
      path: '',
      component: AppApplicationsComponent,
      data: { title: 'Applications', breadcrumb: 'Applications' }
    }] 
  }
]; 