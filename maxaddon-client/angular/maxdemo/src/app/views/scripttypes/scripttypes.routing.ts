import { Routes } from '@angular/router';

import { AppScripttypesComponent } from './app-scripttypes/app-scripttypes.component';


export const ScripttypesRoutes: Routes = [
  {
    path: '',
    children: [{
      path: '',
      component: AppScripttypesComponent,
      data: { title: 'Script Type', breadcrumb: 'Script Type' }
    }] 
  }
]; 