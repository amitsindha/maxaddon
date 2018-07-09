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
import { AppScriptsPopupComponent } from "./app-scripts/app-scripts-popup/app-scripts-popup.component";
import { AppScriptsComponent } from './app-scripts/app-scripts.component';
import { ScriptsRoutes } from "./scripts.routing";
import { ScriptsService } from "./scripts.service";

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
    RouterModule.forChild(ScriptsRoutes)
  ],
  declarations: [AppScriptsComponent, AppScriptsPopupComponent],
  providers: [ScriptsService],
  entryComponents: [AppScriptsPopupComponent]
})
export class ScriptsModule { }

