export enum RelationType {
  UNKNOWN, CHILD, PARENT
}

export class Relation {
  human1LocalRef: string = ""; // used for route (abbreviation)
  human2LocalRef: string = ""; // used for route (abbreviation)
  type: RelationType = RelationType.UNKNOWN;
  dateOfBegin? : string = undefined;
  dateOfEnd? : string = undefined;

  initForEmtpy(){}
  loaded(human1LocalRef: string, human2LocalRef: string, type: RelationType, dateOfBegin: string, dateOfEnd: string)
  {
    this.human1LocalRef = human1LocalRef;
    this.human2LocalRef = human2LocalRef;
    this.dateOfBegin = dateOfBegin == "yyyy/mm/dd" ? undefined : dateOfBegin;
    this.dateOfEnd = dateOfEnd == "yyyy/mm/dd" ? undefined : dateOfEnd;
    this.type = type;
    return this;
  }
  newRelation(human1LocalRef: string, human2LocalRef: string, type: RelationType)
  {
    this.human1LocalRef = human1LocalRef;
    this.human2LocalRef = human2LocalRef;
    this.type = type;
    return this;
  }
}
