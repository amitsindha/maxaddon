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
import { AppClustersPopupComponent } from "./app-clusters/app-clusters-popup/app-clusters-popup.component";
import { AppClustersComponent } from './app-clusters/app-clusters.component';
import { ClustersRoutes } from "./clusters.routing";
import { ClustersService } from "./clusters.service";

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
    RouterModule.forChild(ClustersRoutes)
  ],
  declarations: [AppClustersComponent, AppClustersPopupComponent],
  providers: [ClustersService],
  entryComponents: [AppClustersPopupComponent]
})
export class ClustersModule { }

