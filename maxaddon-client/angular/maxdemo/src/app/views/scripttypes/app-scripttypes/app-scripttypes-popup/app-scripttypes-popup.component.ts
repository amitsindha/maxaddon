import { egretAnimations } from "../../../../shared/animations/egret-animations";
import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatSnackBar } from '@angular/material';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { Router } from "@angular/router";

@Component({
  selector: 'app-scripttypes-popup',
  templateUrl: './app-scripttypes-popup.component.html',
  styleUrls: ['./app-scripttypes-popup.component.css'],
  animations: egretAnimations
})

export class AppScripttypesPopupComponent implements OnInit {
  public itemForm: FormGroup;
  
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    public dialogRef: MatDialogRef<AppScripttypesPopupComponent>,
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
      details: [item.details || ''],        
      //id: [item.id || ''],      
      isCustom: [item.isCustom || true]
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