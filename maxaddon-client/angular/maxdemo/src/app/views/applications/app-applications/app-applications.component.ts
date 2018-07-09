import { egretAnimations } from "../../../shared/animations/egret-animations";
import { AppConfirmService } from "../../../shared/services/app-confirm/app-confirm.service";
import { AppLoaderService } from "../../../shared/services/app-loader/app-loader.service";
import { ApplicationsService } from "../applications.service";
import { AppApplicationsPopupComponent } from "./app-applications-popup/app-applications-popup.component";
import { Component, OnInit, OnDestroy } from '@angular/core';
import { MatDialog, MatDialogRef } from "@angular/material/dialog";
import { MatSnackBar } from "@angular/material/snack-bar";
import { Subscription } from "rxjs";
import { Router } from "@angular/router";

@Component({
  selector: 'app-applications',
  templateUrl: './app-applications.component.html',
  styleUrls: ['./app-applications.component.css'],
  animations: egretAnimations
})
export class AppApplicationsComponent implements OnInit, OnDestroy {
  
  public items: any[];
  public getItemSub: Subscription; 
  
  constructor(
    private dialog: MatDialog,
    private snack: MatSnackBar,
    private applicationsService: ApplicationsService,
    private confirmService: AppConfirmService,
    private loader: AppLoaderService,
    private router: Router,
  ) { }

  ngOnInit() {
    if(!localStorage.getItem('ngFuseToken')) {
        this.router.navigate(['/sessions/signin']); 
    } else {
        this.getItems()
    }      
  }
  ngOnDestroy() {
    if (this.getItemSub) {
      this.getItemSub.unsubscribe()
    }
  }
  
  getItems() {
    this.getItemSub = this.applicationsService.getItems()
      .subscribe(data => {
        console.log(data);
        this.items = data;
      }, error => {
            this.snack.open('Error ocured: Something went wrong to connect to API!', 'OK')
         }
      )
  }
  
  deleteItem(row) {

     if(!localStorage.getItem('ngFuseToken')) {
        this.snack.open('Session Expired !!! ', 'OK');
        //this.router.navigate(['/sessions/signin']); 
    } else {
        this.confirmService.confirm({message: `Delete ${row.name}?`})
      .subscribe(res => {
        if (res) {
          this.loader.open();
          this.applicationsService.removeItem(row.id)
            .subscribe(data => {
              this.getItems(); // added for rest services 
              //this.items = data; // commented for rest services 
              this.loader.close();
              this.snack.open('Application deleted!', 'OK', { duration: 4000 })
            }, error => {
                this.loader.close();
                this.snack.open('Error ocured: Something went wrong to connect to API!', 'OK')
            })
        } 
      })
    }   
  }
  
  openPopUp(data: any = {}, isNew?) {
    let title = isNew ? 'Add new Application' : 'Update Application';
    let dialogRef: MatDialogRef<any> = this.dialog.open(AppApplicationsPopupComponent, {
      width: '50%',
      height: '100%',
      // maxWidth: '100%',
      // maxHeight: '100%',
      position: {
        top: '',
        bottom: '',
        left: '50%',
        right: ''
      },  
      disableClose: true,
      data: { title: title, payload: data }
    })
    dialogRef.afterClosed()
      .subscribe(res => {
        if(!res) {
          // If user press cancel
          return;
        }
        this.loader.open();
        if (isNew) {
          this.applicationsService.addItem(res)
            .subscribe(data => {   
              this.getItems(); // added for rest services 
              //this.items = data; // commented for rest services                              
              this.loader.close();
              this.snack.open('Application Added!', 'OK', { duration: 4000 })
            }, error => {
                this.loader.close();                
                this.snack.open('Error ocured: Something went wrong to connect to API!', 'OK')
            }) 
        } else {
          /*this.applicationsService.updateItem(data._id, res)
            .subscribe(data => {
              this.items = data;
              this.loader.close();
              this.snack.open('Application Updated!', 'OK', { duration: 4000 })
            })*/
            this.applicationsService.updateItem(data.id, res)
            .subscribe(data => {   
              this.getItems(); // added for rest services 
              //this.items = data; // commented for rest services                               
              this.loader.close();
              this.snack.open('Application Updated!', 'OK', { duration: 4000 })
            }, error => {
                this.loader.close();
                this.snack.open('Error ocured: Something went wrong to connect to API!', 'OK')
            }) 
        }
      })
  }

}
