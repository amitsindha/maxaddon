import { egretAnimations } from "../../../shared/animations/egret-animations";
import { AppConfirmService } from "../../../shared/services/app-confirm/app-confirm.service";
import { AppLoaderService } from "../../../shared/services/app-loader/app-loader.service";
import { ActionsService } from "../actions.service";
import { AppActionsPopupComponent } from "./app-actions-popup/app-actions-popup.component";
import { Component, OnInit, OnDestroy } from '@angular/core';
import { MatDialog, MatDialogRef } from "@angular/material/dialog";
import { MatSnackBar } from "@angular/material/snack-bar";
import { Subscription } from "rxjs";
import { Router } from "@angular/router";

@Component({
  selector: 'app-actions',
  templateUrl: './app-actions.component.html',
  styleUrls: ['./app-actions.component.css'],
  animations: egretAnimations
})
export class AppActionsComponent implements OnInit, OnDestroy {
  
  public items: any[];
  public getItemSub: Subscription; 
  
  constructor(
    private dialog: MatDialog,
    private snack: MatSnackBar,
    private actionsService: ActionsService,
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
    this.getItemSub = this.actionsService.getItems()
      .subscribe(data => {
        console.log(data);
        this.items = data;
      }, error => {
            this.snack.open('Error ocured: Something went wrong to connect to API!', 'OK')
         }
      )
  }
  
   
  
  executeItem(row) {

     if(!localStorage.getItem('ngFuseToken')) {
        this.snack.open('Session Expired !!! ', 'OK');
        //this.router.navigate(['/sessions/signin']); 
    } else {
        this.confirmService.confirm({message: `Do you want to execute script - ${row.name}?`})
      .subscribe(res => {
        if (res) {
          this.loader.open();
          this.actionsService.executeItem(row.id, row)
            .subscribe(data => {
              this.getItems(); // added for rest services 
              //this.items = data; // commented for rest services 
              this.loader.close();
              this.snack.open('Script Execution Started!', 'OK', { duration: 4000 })
            }, error => {
                this.loader.close();
                this.snack.open('Error ocured: Something went wrong to connect to API!', 'OK')
            })
        } 
      })
    }   
  }
  
  openPopUp(data: any = {}) {    
    let dialogRef: MatDialogRef<any> = this.dialog.open(AppActionsPopupComponent, {
      width: '80%',
      height: '100%',
      position: {
        top: '',
        bottom: '',
        left: '80%',
        right: ''
      },  
      disableClose: true,
      data: { title: 'Filter Data', payload: data }
    })
    dialogRef.afterClosed()
      .subscribe(res => {
        if(!res) {
          // If user press cancel
          return;
        }
        this.loader.open();        
          this.actionsService.filterItem(res)
            .subscribe(data => {   
              //this.getFilterItems(); // added for rest services 
              this.items = data; // commented for rest services                              
              this.loader.close();
              this.snack.open('Filter Applied!', 'OK', { duration: 4000 })
            }, error => {
                this.loader.close();                
                this.snack.open('Error ocured: Something went wrong to connect to API!', 'OK')
            })           
      })
  }

}
