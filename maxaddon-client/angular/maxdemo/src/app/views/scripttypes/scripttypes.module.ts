import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { 
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
import { AppScripttypesPopupComponent } from "./app-scripttypes/app-scripttypes-popup/app-scripttypes-popup.component";
import { AppScripttypesComponent } from './app-scripttypes/app-scripttypes.component';
import { ScripttypesRoutes } from "./scripttypes.routing";
import { ScripttypesService } from "./scripttypes.service";

@NgModule({
  imports: [
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
    RouterModule.forChild(ScripttypesRoutes)
  ],
  declarations: [AppScripttypesComponent, AppScripttypesPopupComponent],
  providers: [ScripttypesService],
  entryComponents: [AppScripttypesPopupComponent]
})
export class ScripttypesModule { }

