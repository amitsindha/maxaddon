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
import { AppActionsPopupComponent } from "./app-actions/app-actions-popup/app-actions-popup.component";
import { AppActionsComponent } from './app-actions/app-actions.component';
import { ActionsRoutes } from "./actions.routing";
import { ActionsService } from "./actions.service";

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
    RouterModule.forChild(ActionsRoutes)
  ],
  declarations: [AppActionsComponent, AppActionsPopupComponent],
  providers: [ActionsService],
  entryComponents: [AppActionsPopupComponent]
})
export class ActionsModule { }

