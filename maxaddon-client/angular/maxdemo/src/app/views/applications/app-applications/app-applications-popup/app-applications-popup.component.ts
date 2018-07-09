import { egretAnimations } from "../../../../shared/animations/egret-animations";
import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatSnackBar } from '@angular/material';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { Router } from "@angular/router";
import { Observable, Subscription } from "rxjs";
import { ApplicationsService } from "../../applications.service";

@Component({
  selector: 'app-applications-popup',
  templateUrl: './app-applications-popup.component.html',
  styleUrls: ['./app-applications-popup.component.css'],
  animations: egretAnimations
})

export class AppApplicationsPopupComponent implements OnInit {
  public itemForm: FormGroup;
  public getServerItemSub: Subscription;
  public serverItems: any[];  
  selectedServerValue: string;
  
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    public dialogRef: MatDialogRef<AppApplicationsPopupComponent>,
	private applicationsService: ApplicationsService,
    private fb: FormBuilder,
    private router: Router,
    private snack: MatSnackBar,
  ) { }
  
  ngOnInit() {   
	this.getServerItems();  	
    this.buildItemForm(this.data.payload)
  }
  
  getServerItems() {
    this.getServerItemSub = this.applicationsService.getServerList()
      .subscribe(data => {
        this.serverItems = data;
      })
  } 
  
  buildItemForm(item) {
	this.selectedServerValue = item.serverId;  
	this.itemForm = this.fb.group({
      name: [item.name || '', Validators.required],
      details: [item.details || ''],
      serverId: [this.serverItems || '', Validators.required]   	
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