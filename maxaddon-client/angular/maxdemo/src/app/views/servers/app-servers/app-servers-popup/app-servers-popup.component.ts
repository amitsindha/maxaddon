import { egretAnimations } from "../../../../shared/animations/egret-animations";
import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatSnackBar } from '@angular/material';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { Router } from "@angular/router";

@Component({
  selector: 'app-servers-popup',
  templateUrl: './app-servers-popup.component.html',
  styleUrls: ['./app-servers-popup.component.css'],
  animations: egretAnimations
})

export class AppServersPopupComponent implements OnInit {
  public itemForm: FormGroup;
  
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    public dialogRef: MatDialogRef<AppServersPopupComponent>,
    private fb: FormBuilder,
    private router: Router,
    private snack: MatSnackBar,
  ) { }
  
  ngOnInit() {      
    this.buildItemForm(this.data.payload)
  }
  
  buildItemForm(item) {
    this.itemForm = this.fb.group({
      name: [item.name || '', Validators.required],
      host: [item.host || '', Validators.required],
      ip: [item.ip || '', Validators.required],
      os: [item.os || '', Validators.required],
      //hostApp: [item.hostApp || '', Validators.required],            
      //hostAppServer: [item.hostAppServer || '', Validators.required],   
      //appServerPort: [item.appServerPort || '', Validators.required],   
      //isAppServerSecure: [item.isAppServerSecure || '', Validators.required],   
      //appServerURL: [item.appServerURL || '', Validators.required],         
      isVM: [item.isVM || ''],
      core: [item.core || ''],
      ram: [item.ram || ''],
      hdd: [item.hdd || ''],      
      //id: [item.id || ''],      
      isActive: [item.isActive || false]
    })
  }
  
  submit() {
    if(!localStorage.getItem('ngFuseToken')) {
        this.snack.open('Session Expired !!! ', 'OK');
        //this.router.navigate(['/sessions/signin']); 
    } else {
        this.dialogRef.close(this.itemForm.value)
    }  
  }
  
}