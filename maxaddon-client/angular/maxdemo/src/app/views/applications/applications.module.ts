import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { 
  MatSelectModule,
  MatInputModule,
  MatIconModule,
  MatCardModule,
  MatMenuModule,
  MatButtonModule,
  MatChipsModule,
  MatListModule,
  MatTooltipModule,
  MatDialogModule,
  MatSnackBarModule,
  MatSlideToggleModule
 } from '@angular/material';
import { FlexLayoutModule } from '@angular/flex-layout';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { SharedModule } from '../../shared/shared.module';
import { AppApplicationsPopupComponent } from "./app-applications/app-applications-popup/app-applications-popup.component";
import { AppApplicationsComponent } from './app-applications/app-applications.component';
import { ApplicationsRoutes } from "./applications.routing";
import { ApplicationsService } from "./applications.service";

@NgModule({
  imports: [
    MatSelectModule,
    CommonModule,
    ReactiveFormsModule,
    FlexLayoutModule,
    NgxDatatableModule,
    MatInputModule,
    MatIconModule,
    MatCardModule,
    MatMenuModule,
    MatButtonModule,
    MatChipsModule,
    MatListModule,
    MatTooltipModule,
    MatDialogModule,
    MatSnackBarModule,
    MatSlideToggleModule,
    SharedModule,
    RouterModule.forChild(ApplicationsRoutes)
  ],
  declarations: [AppApplicationsComponent, AppApplicationsPopupComponent],
  providers: [ApplicationsService],
  entryComponents: [AppApplicationsPopupComponent]
})
export class ApplicationsModule { }

