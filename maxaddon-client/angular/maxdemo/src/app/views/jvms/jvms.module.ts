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
import { AppJvmsPopupComponent } from "./app-jvms/app-jvms-popup/app-jvms-popup.component";
import { AppJvmsComponent } from './app-jvms/app-jvms.component';
import { JvmsRoutes } from "./jvms.routing";
import { JvmsService } from "./jvms.service";

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
    RouterModule.forChild(JvmsRoutes)
  ],
  declarations: [AppJvmsComponent, AppJvmsPopupComponent],
  providers: [JvmsService],
  entryComponents: [AppJvmsPopupComponent]
})
export class JvmsModule { }

