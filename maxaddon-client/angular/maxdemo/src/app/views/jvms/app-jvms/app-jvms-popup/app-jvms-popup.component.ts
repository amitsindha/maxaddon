import { egretAnimations } from "../../../../shared/animations/egret-animations";
import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatSnackBar } from '@angular/material';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { Router } from "@angular/router";
import { Observable, Subscription } from "rxjs";
import { JvmsService } from "../../jvms.service";

@Component({
  selector: 'app-jvms-popup',
  templateUrl: './app-jvms-popup.component.html',
  styleUrls: ['./app-jvms-popup.component.css'],
  animations: egretAnimations
})

export class AppJvmsPopupComponent implements OnInit {
  public itemForm: FormGroup;
  public getServerItemSub: Subscription;
  public getClusterItemSub: Subscription;
  public serverItems: any[];  
  public clusterItems: any[];  
  selectedServerValue: string;
  selectedClusterValue: string;
  
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    public dialogRef: MatDialogRef<AppJvmsPopupComponent>,
	private jvmsService: JvmsService,
    private fb: FormBuilder,
    private router: Router,
    private snack: MatSnackBar,
  ) { }
  
  ngOnInit() {   
	this.getServerItems();  
	this.getClusterItems();
    this.buildItemForm(this.data.payload)
  }
  
  getServerItems() {
    this.getServerItemSub = this.jvmsService.getServerList()
      .subscribe(data => {
        this.serverItems = data;
      })
  } 
  
  getClusterItems() {
    this.getClusterItemSub = this.jvmsService.getClusterList()
      .subscribe(data => {
        this.clusterItems = data;
      })
  } 
  
  buildItemForm(item) {
	this.selectedServerValue = item.serverId;  
	this.selectedClusterValue = item.clusterId; 
	this.itemForm = this.fb.group({
      name: [item.name || '', Validators.required],
      serverId: [this.serverItems || '', Validators.required],
	  clusterId: [this.clusterItems || '', Validators.required] 	  
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