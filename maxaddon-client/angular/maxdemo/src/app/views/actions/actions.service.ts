import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable, of } from 'rxjs';
import { delay } from 'rxjs/operators';
import { environment } from 'environments/environment';

@Injectable()
export class ActionsService {
  items: any[];
  constructor(private http:HttpClient) {
       
  }
  
  //******* Implement your APIs ********
  getItems(): Observable<any> {
    return this.http.get(environment.apiURL+'/rest/api/script/get');
  }
 
  filterItem(item): Observable<any> {
    const headers = new HttpHeaders({
      'Content-type' : 'application/json'
    });    
	console.log('item data filter ::: '+JSON.stringify(item));
    return this.http.post(environment.apiURL+'/rest/api/script/filter', item, {headers: headers});    
  } 
  
  executeItem(id, item): Observable<any> {
    const headers = new HttpHeaders({
      'Content-type' : 'application/json'
    });    
	console.log('item data filter ::: '+JSON.stringify(item));    
	return this.http.post(environment.apiURL+'/rest/api/script/'+id+'/execute', item, {headers: headers});  	
  } 
  
  addItem(item): Observable<any> {
    const headers = new HttpHeaders({
      'Content-type' : 'application/json'
    });    
	console.log('item data add ::: '+JSON.stringify(item));
    return this.http.post(environment.apiURL+'/rest/api/script/create', item, {headers: headers});    
  } 
  
  updateItem(id, item): Observable<any> {
    const headers = new HttpHeaders({
      'Content-type' : 'application/json'
    });
    item.id=id;  
	console.log('item data update ::: '+JSON.stringify(item));
    return this.http.put(environment.apiURL+'/rest/api/script/'+id+'/edit', item, {headers: headers});    
  } 
  
  removeItem(id): Observable<any> {
    const headers = new HttpHeaders({ 
      'Content-type' : 'application/json'
    });
    return this.http.delete(environment.apiURL+'/rest/api/script/'+id+'/delete', {headers: headers});    
  } 
  
  public getServerList(): Observable<any> { 
    return this.http.get(environment.apiURL+'/rest/api/server/get'); 
  } 
  
  public getDefaultScriptTypeList(): Observable<any> { 
    return this.http.get(environment.apiURL+'/rest/api/scripttype/get/default'); 
  } 

  public getCustomScriptTypeList(): Observable<any> { 
    return this.http.get(environment.apiURL+'/rest/api/scripttype/get/custom'); 
  }  	
  
  public getClusterList(): Observable<any> { 
    return this.http.get(environment.apiURL+'/rest/api/cluster/get'); 
  }
  
  public getJvmList(): Observable<any> { 
    return this.http.get(environment.apiURL+'/rest/api/jvm/get'); 
  }
  
}
