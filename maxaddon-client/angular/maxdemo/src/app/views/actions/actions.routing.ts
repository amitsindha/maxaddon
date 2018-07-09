import { Routes } from '@angular/router';

import { AppActionsComponent } from './app-actions/app-actions.component';


export const ActionsRoutes: Routes = [
  {
    path: '',
    children: [{
      path: '',
      component: AppActionsComponent,
      data: { title: 'Actions', breadcrumb: 'Actions' }
    }] 
  }
]; 