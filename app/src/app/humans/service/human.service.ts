import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { Human } from 'src/app/humans/store';
import { ApiService } from 'src/app/core/service';

interface HumanDataRO {
  name : string;
  userRef : string;
  dateOfBirth? : string;
  dateOfDeath? : string;
  gender? : string;
}

interface HumanInfoRO {
  age? : number;
}

interface HumanUpdateRO {
  name? : string;
  dateOfBirth? : string;
  dateOfDeath? : string;
  gender? : string;
}

interface HumanRO {
  refLocal : string;
  refGlobal : string;
  resourceURI: string;
  data : HumanDataRO;
  info : HumanInfoRO;
}

@Injectable({ providedIn: 'root' })
export class HumanService {
  constructor(private apiService: ApiService) {}

  getHumans(userRef:string): Observable<Human[]> {
    return this.apiService
      .get<HumanRO[]>(`/humans?userRef=${userRef}`)
      .pipe(map(ros => this.convertListHumanRO(ros)));
  }

  getParents(childLocalRef:string): Observable<Human[]> {
    return this.apiService
      .get<HumanRO[]>(`/humans?child=${childLocalRef}`)
      .pipe(map(ros => this.convertListHumanRO(ros)));
  }

  getHuman(externalID: string): Observable<Human> {
    return this.apiService
      .get<HumanRO>(`/humans/${externalID}`)
      .pipe(map(convertHumanRO));
  }
  addHuman(externalID: string, data : HumanDataRO): Observable<{}> {
    return this.apiService
      .put(`/humans/${externalID}`, data);
  }

  updateHuman(externalID: string, data : HumanUpdateRO): Observable<{}> {
    return this.apiService
      .patch(`/humans/${externalID}`, data);
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
  return new Human().loaded(ro.resourceURI, ro.refLocal, ro.data.name, ro.data.dateOfBirth, ro.data.dateOfDeath, ro.info.age, ro.data.gender);
}
