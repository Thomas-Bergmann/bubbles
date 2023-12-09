import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { ApiService } from 'src/app/core/service';
import { Relation } from '../store';
import { Human } from 'src/app/humans';


@Injectable({ providedIn: 'root' })
export class RelationService {
  constructor(private apiService: ApiService) {}
  getChildren(parentLocalRef:string): Observable<string[]> {
    return this.apiService
      .get<string[]>(`/humans/${parentLocalRef}/children`);
  }
  createChild(parentLocalRef: string, childLocalRef : string): Observable<{}> {
    return this.apiService
      .put(`/humans/${parentLocalRef}/children/${childLocalRef}`);
  }

  deleteChild(parentLocalRef: string, childLocalRef : string): Observable<{}> {
    return this.apiService
    .delete(`/humans/${parentLocalRef}/children/${childLocalRef}`);
  }
}
