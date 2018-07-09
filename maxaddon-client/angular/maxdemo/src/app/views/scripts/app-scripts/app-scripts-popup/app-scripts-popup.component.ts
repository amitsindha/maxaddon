import { egretAnimations } from "../../../../shared/animations/egret-animations";
import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatSnackBar } from '@angular/material';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { Router } from "@angular/router";
import { Observable, Subscription } from "rxjs";
import { ScriptsService } from "../../scripts.service";

@Component({
  selector: 'app-scripts-popup',
  templateUrl: './app-scripts-popup.component.html',
  styleUrls: ['./app-scripts-popup.component.css'],
  animations: egretAnimations
})

export class AppScriptsPopupComponent implements OnInit {
  public itemForm: FormGroup;
  public getServerItemSub: Subscription;
  public getScriptTypeItemSub: Subscription;
  public getCustomScriptTypeItemSub: Subscription;
  public getClusterItemSub: Subscription;
  public getJvmItemSub: Subscription;
  public serverItems: any[];  
  public scriptTypeItems: any[];  
  public customScriptTypeItems: any[];  
  public clusterItems: any[];  
  public jvmItems: any[];  
  selectedServerValue: string;
  selectedScriptTypeValue: string;
  selectedCustomScriptTypeValue: string;
  selectedClusterValue: string;
  selectedJvmValue: string;
  
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    public dialogRef: MatDialogRef<AppScriptsPopupComponent>,
	private scriptsService: ScriptsService,
    private fb: FormBuilder,
    private router: Router,
    private snack: MatSnackBar,
  ) { }
  
  ngOnInit() {   
	this.getServerItems();  
	this.getDefaultScriptTypeList();
	this.getCustomScriptTypeList();
	//this.getClusterItems();
	//this.getJvmItems();
    this.buildItemForm(this.data.payload)
  }
  
  getServerItems() {
    this.getServerItemSub = this.scriptsService.getServerList()
      .subscribe(data => {
        this.serverItems = data;
      })
  } 
  
  getDefaultScriptTypeList() {
    this.getScriptTypeItemSub = this.scriptsService.getDefaultScriptTypeList()
      .subscribe(data => {
        this.scriptTypeItems = data;
      })
  }

  getCustomScriptTypeList() {
    this.getCustomScriptTypeItemSub = this.scriptsService.getCustomScriptTypeList()
      .subscribe(data => {
        this.customScriptTypeItems = data;
      })
  }  	 
  
  getClusterItems() {
    this.getClusterItemSub = this.scriptsService.getClusterList()
      .subscribe(data => {
        this.clusterItems = data;
      })
  } 
  
  getJvmItems() {
    this.getJvmItemSub = this.scriptsService.getJvmList()
      .subscribe(data => {
        this.jvmItems = data;
      })
  } 
  
  buildItemForm(item) {
	this.selectedServerValue = item.serverId;  
	this.selectedScriptTypeValue = item.scriptTypeId; 
	this.selectedCustomScriptTypeValue = item.name; 
	//this.selectedClusterValue = item.clusterId; 
	//this.selectedJvmValue = item.jvmId; 
	this.itemForm = this.fb.group({
      //name: [item.name || '', Validators.required],
	  details: [item.details || ''],
	  content: [item.content || ''],
      serverId: [this.serverItems || '', Validators.required],
	  scriptTypeId: [this.scriptTypeItems || '', Validators.required],
	  name: [this.customScriptTypeItems || '', Validators.required]
	  //clusterId: [this.clusterItems || ''],
	  //jvmId: [this.jvmItems || ''],
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