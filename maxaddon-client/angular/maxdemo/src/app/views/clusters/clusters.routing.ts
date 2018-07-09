import { Routes } from '@angular/router';

import { AppClustersComponent } from './app-clusters/app-clusters.component';


export const ClustersRoutes: Routes = [
  {
    path: '',
    children: [{
      path: '',
      component: AppClustersComponent,
      data: { title: 'Clusters', breadcrumb: 'Clusters' }
    }] 
  }
]; 