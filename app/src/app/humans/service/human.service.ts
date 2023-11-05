import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { Human } from 'src/app/humans/store';
import { ApiService } from 'src/app/core/service';

interface HumanDataRO {
  name : string;
  userRef : string;
}

interface HumanCreateRO extends HumanDataRO {
  userRef : string;
}

interface HumanRO {
  refLocal : string;
  refGlobal : string;
  resourceURI: string;
  data : HumanDataRO;
}

@Injectable({ providedIn: 'root' })
export class HumanService {
  constructor(private apiService: ApiService) {}

  getHumans(userRef:string): Observable<Human[]> {
    return this.apiService
      .get<HumanRO[]>(`/humans?userRef=${userRef}`)
      .pipe(map(ros => this.convertListHumanRO(ros)));
  }

  addHuman(externalID: string, data : HumanCreateRO): Observable<{}> {
    return this.apiService
      .put(`/humans/${externalID}`, data);
  }

  deleteHuman(human : Human): Observable<{}> {
    return this.apiService.delete(human.resourceURI);
  }

  convertListHumanRO(ros : HumanRO[]): Human[]
  {
    return ros.map(ro => convertHumanRO(ro));
  }
}

function convertHumanRO(ro : HumanRO): Human
{
  return new Human().init(ro.resourceURI, ro.refLocal, ro.data.name);
}
