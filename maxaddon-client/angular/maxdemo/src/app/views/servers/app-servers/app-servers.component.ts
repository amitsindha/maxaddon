import { egretAnimations } from "../../../shared/animations/egret-animations";
import { AppConfirmService } from "../../../shared/services/app-confirm/app-confirm.service";
import { AppLoaderService } from "../../../shared/services/app-loader/app-loader.service";
import { ServersService } from "../servers.service";
import { AppServersPopupComponent } from "./app-servers-popup/app-servers-popup.component";
import { Component, OnInit, OnDestroy } from '@angular/core';
import { MatDialog, MatDialogRef } from "@angular/material/dialog";
import { MatSnackBar } from "@angular/material/snack-bar";
import { Subscription } from "rxjs";
import { Router } from "@angular/router";

@Component({
  selector: 'app-servers',
  templateUrl: './app-servers.component.html',
  styleUrls: ['./app-servers.component.css'],
  animations: egretAnimations
})
export class AppServersComponent implements OnInit, OnDestroy {
  
  public items: any[];
  public getItemSub: Subscription; 
  
  constructor(
    private dialog: MatDialog,
    private snack: MatSnackBar,
    private serversService: ServersService,
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
    this.getItemSub = this.serversService.getItems()
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
          this.serversService.removeItem(row.id)
            .subscribe(data => {
              this.getItems(); // added for rest services 
              //this.items = data; // commented for rest services 
              this.loader.close();
              this.snack.open('Server deleted!', 'OK', { duration: 4000 })
            }, error => {
                this.loader.close();
                this.snack.open('Error ocured: Something went wrong to connect to API!', 'OK')
            })
        } 
      })
    }   
  }
  
  openPopUp(data: any = {}, isNew?) {
    let title = isNew ? 'Add new Server' : 'Update Server';
    let dialogRef: MatDialogRef<any> = this.dialog.open(AppServersPopupComponent, {
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
          this.serversService.addItem(res)
            .subscribe(data => {   
              this.getItems(); // added for rest services 
              //this.items = data; // commented for rest services                              
              this.loader.close();
              this.snack.open('Server Added!', 'OK', { duration: 4000 })
            }, error => {
                this.loader.close();                
                this.snack.open('Error ocured: Something went wrong to connect to API!', 'OK')
            }) 
        } else {
          /*this.serversService.updateItem(data._id, res)
            .subscribe(data => {
              this.items = data;
              this.loader.close();
              this.snack.open('Server Updated!', 'OK', { duration: 4000 })
            })*/
            this.serversService.updateItem(data.id, res)
            .subscribe(data => {   
              this.getItems(); // added for rest services 
              //this.items = data; // commented for rest services                               
              this.loader.close();
              this.snack.open('Server Updated!', 'OK', { duration: 4000 })
            }, error => {
                this.loader.close();
                this.snack.open('Error ocured: Something went wrong to connect to API!', 'OK')
            }) 
        }
      })
  }

}
