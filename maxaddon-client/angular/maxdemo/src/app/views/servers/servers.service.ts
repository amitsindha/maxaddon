import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable, of } from 'rxjs';
import { delay } from 'rxjs/operators';
import { environment } from 'environments/environment';

@Injectable()
export class ServersService {
  items: any[];
  constructor(private http:HttpClient) {
       
  }
  
  //******* Implement your APIs ********
  getItems(): Observable<any> {
    return this.http.get(environment.apiURL+'/rest/api/server/get');
  }
 
  addItem(item): Observable<any> {
    const headers = new HttpHeaders({
      'Content-type' : 'application/json'
    });    
    return this.http.post(environment.apiURL+'/rest/api/server/create', item, {headers: headers});    
  } 

  updateItem(id, item): Observable<any> {
    const headers = new HttpHeaders({
      'Content-type' : 'application/json'
    });
    item.id=id;  
    return this.http.put(environment.apiURL+'/rest/api/server/edit/'+id, item, {headers: headers});    
  } 
  
  removeItem(id): Observable<any> {
    const headers = new HttpHeaders({
      'Content-type' : 'application/json'
    });
    return this.http.delete(environment.apiURL+'/rest/api/server/delete/'+id, {headers: headers});    
  } 
  
}
