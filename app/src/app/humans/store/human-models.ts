
export class Human {
  resourceURI: string = ""; // used for service
  localRef: string = ""; // used for route (abbreviation)
  name: string = "";
  dateOfBirth : string = "";
  dateOfDeath : string = "";

  initForEmtpy(){}
  init(resourceURI: string, localRef: string, name : string, dateOfBirth: string, dateOfDeath: string)
  {
    this.resourceURI = resourceURI;
    this.localRef = localRef;
    this.name = name;
    this.dateOfBirth = dateOfBirth;
    this.dateOfDeath = dateOfDeath;
    return this;
  }
  newHuman(name: string, dateOfBirth: string, dateOfDeath: string)
  {
    var localRef = name;
    this.resourceURI =`/humans/${localRef}`;
    this.localRef = localRef;
    this.name = name;
    this.dateOfBirth = dateOfBirth;
    this.dateOfDeath = dateOfDeath;
    return this;
  }
}
