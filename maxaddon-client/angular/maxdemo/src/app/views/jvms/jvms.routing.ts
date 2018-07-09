import { Routes } from '@angular/router';

import { AppJvmsComponent } from './app-jvms/app-jvms.component';


export const JvmsRoutes: Routes = [
  {
    path: '',
    children: [{
      path: '',
      component: AppJvmsComponent,
      data: { title: 'Jvms', breadcrumb: 'Jvms' }
    }] 
  }
]; 