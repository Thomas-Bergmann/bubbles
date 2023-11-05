
export class Bubble {
  resourceURI: string = ""; // used for service
  localRef: string = ""; // used for route (abbreviation)
  name: string = "";

  initForEmtpy(){}
  init(resourceURI: string, localRef: string, name : string)
  {
    this.resourceURI = resourceURI;
    this.localRef = localRef;
    this.name = name;
    return this;
  }
  newBubble(name: string)
  {
    var localRef = name;
    this.resourceURI =`/bubbles/${localRef}`;
    this.localRef = localRef;
    this.name = name;
    return this;
  }
}
