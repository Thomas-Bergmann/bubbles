
export class Bubble {
  resourceURI: string = ""; // used for service
  localRef: string = ""; // used for route (abbreviation)
  abbreviation: string = "";
  name: string = "";

  initForEmtpy(){}
  init(resourceURI: string, abbreviation: string, name : string)
  {
    this.resourceURI = resourceURI;
    this.localRef = abbreviation;
    this.abbreviation = abbreviation;
    this.name = name;
    return this;
  }
  newBubble(name: string)
  {
    var abbreviation = name;
    this.resourceURI =`/bubbles/${abbreviation}`;
    this.localRef = abbreviation;
    this.abbreviation = abbreviation;
    this.name = name;
    return this;
  }
}
