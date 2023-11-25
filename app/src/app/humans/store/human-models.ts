export class Human {
  resourceURI: string = ""; // used for service
  localRef: string = ""; // used for route (abbreviation)
  name: string = "";
  dateOfBirth? : string = undefined;
  dateOfDeath? : string = undefined;
  gender? : string = undefined;
  age?: number = undefined;

  initForEmtpy(){}
  loaded(resourceURI: string, localRef: string, name : string, dateOfBirth?: string, dateOfDeath?: string, age?: number, gender?:string)
  {
    this.resourceURI = resourceURI;
    this.localRef = localRef;
    this.name = name;
    this.dateOfBirth = dateOfBirth == "yyyy/mm/dd" ? undefined : dateOfBirth;
    this.dateOfDeath = dateOfDeath == "yyyy/mm/dd" ? undefined : dateOfDeath;
    this.gender = gender;
    this.age = age;
    return this;
  }
  newHuman(name: string, dateOfBirth: string, dateOfDeath: string)
  {
    var localRef = crypto.randomUUID();
    this.resourceURI =`/humans/${localRef}`;
    this.localRef = localRef;
    this.name = name;
    this.dateOfBirth = dateOfBirth;
    this.dateOfDeath = dateOfDeath;
    return this;
  }
}
