import { Routes } from '@angular/router';
import { AdminLayoutComponent } from './shared/components/layouts/admin-layout/admin-layout.component';
import { AuthLayoutComponent } from './shared/components/layouts/auth-layout/auth-layout.component';
import { AuthGuard } from './shared/services/auth/auth.guard';

export const rootRouterConfig: Routes = [
  { 
    path: '', 
    redirectTo: 'dashboard',
    pathMatch: 'full' 
  }, 
  {
    path: '', 
    component: AuthLayoutComponent,
    children: [
      { 
        path: 'sessions', 
        loadChildren: './views/sessions/sessions.module#SessionsModule',
        data: { title: 'Session'} 
      }
    ]
  },
  {
    path: '', 
    component: AdminLayoutComponent,
    canActivate: [AuthGuard],
    children: [
      {
        path: 'dashboard', 
        loadChildren: './views/others/others.module#OthersModule', 
        data: { title: 'Others', breadcrumb: 'OTHERS'}
      },
      {
        path: 'members', 
        loadChildren: './views/members/members.module#MembersModule', 
        data: { title: 'Members', breadcrumb: 'Members'}
      },
      {
        path: 'servers', 
        loadChildren: './views/servers/servers.module#ServersModule', 
        data: { title: 'Servers', breadcrumb: 'Servers'} 
      },
      {
        path: 'clusters', 
        loadChildren: './views/clusters/clusters.module#ClustersModule', 
        data: { title: 'Clusters', breadcrumb: 'Clusters'} 
      },
      {
        path: 'jvms', 
        loadChildren: './views/jvms/jvms.module#JvmsModule', 
        data: { title: 'JVM', breadcrumb: 'JVM'} 
      },
      {
        path: 'applications', 
        loadChildren: './views/applications/applications.module#ApplicationsModule', 
        data: { title: 'Applications', breadcrumb: 'Applications'} 
      },
      {
        path: 'scripttypes', 
        loadChildren: './views/scripttypes/scripttypes.module#ScripttypesModule', 
        data: { title: 'Script Type', breadcrumb: 'Script Type'} 
      },
      {
        path: 'scripts', 
        loadChildren: './views/scripts/scripts.module#ScriptsModule', 
        data: { title: 'Scripts', breadcrumb: 'Scripts'} 
      },
      {
        path: 'actions', 
        loadChildren: './views/actions/actions.module#ActionsModule', 
        data: { title: 'Actions', breadcrumb: 'Actions'} 
      },
      {
        path: 'scheduler', 
        loadChildren: './views/app-calendar/app-calendar.module#AppCalendarModule', 
        data: { title: 'Scheduler', breadcrumb: 'Scheduler'}
      }    
    ]
  },
  { 
    path: '**', 
    redirectTo: 'sessions/404'
  }
];