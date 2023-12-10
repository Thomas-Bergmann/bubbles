import { AfterViewInit, Component, ElementRef, Input, OnChanges, SimpleChanges, ViewChild } from '@angular/core';

import { Human } from 'src/app/humans/store/human-models';
@Component({
  selector: 'canvasHuman',
  templateUrl: './canvasHuman.component.html',
  styleUrls: ['./canvasHuman.component.sass']
})
export class CanvasHumanComponent implements AfterViewInit, OnChanges {
  @Input() human!: Human;
  @Input() selectedHuman?: Human;
  @ViewChild('human') canvas: ElementRef = {} as ElementRef;
  
  context?: CanvasRenderingContext2D = undefined;
  centerPosX = 80;
  centerPosY = 50;
  radius = 10;
  ngOnChanges(changes: SimpleChanges): void {
      this.ngAfterViewInit();
  }
  ngAfterViewInit(): void {
    this.context = this.canvas.nativeElement.getContext('2d');
    if (this.context !== undefined)
    {
      this.context.stroke();
      this.context.fillStyle = "white"
      this.context.fillRect(0,0, this.centerPosX*2, this.centerPosY*2);
      this.context.stroke();
      this.strokeHuman(this.context);
    }
  }
  private strokeHuman(context: CanvasRenderingContext2D) : void
  {
    context.fillStyle = "black"
    this.strokeGenderSymbol(context);
    this.strokeDeathAtSymbol(context);
    this.strokeAge(context);
    this.strokeName(context);
    this.strokeBirthAndDeath(context);
  }
  private strokeGenderSymbol(context: CanvasRenderingContext2D) : void
  {
    if (this.human.gender == "MALE")
    {
      context.strokeRect(this.centerPosX-this.radius,this.centerPosY-this.radius,this.radius * 2,this.radius * 2);
      context.stroke();
    }
    if (this.human.gender == "FEMALE")
    {
      context.beginPath();
      context.arc(this.centerPosX,this.centerPosY,this.radius,0,2*Math.PI);
      context.stroke();
    }
  }
  private strokeDeathAtSymbol(context: CanvasRenderingContext2D) : void
  {
    if (this.human.dateOfDeath === undefined)
    {
      return;
    }
    context.beginPath();
    context.moveTo(this.centerPosX-this.radius,this.centerPosY-this.radius);
    context.lineTo(this.centerPosX+this.radius,this.centerPosY+this.radius);
    context.moveTo(this.centerPosX+this.radius,this.centerPosY-this.radius);
    context.lineTo(this.centerPosX-this.radius,this.centerPosY+this.radius);
    context.stroke();
  }
  private strokeAge(context: CanvasRenderingContext2D) {
    if (this.human.age != undefined)
    {
      context.font = "0.9em serif";
      let adjustPos = context.measureText(this.human.age.toString()).width / 2;
      context.fillText(this.human.age.toString(), this.centerPosX-adjustPos, this.centerPosY+this.radius/2, this.radius*2); 
      context.stroke();
    }
  }
  private strokeName(context: CanvasRenderingContext2D) {
    if (this.human.name != undefined)
    {
      context.font = "0.8em serif";
      let adjustPos = context.measureText(this.human.name).width / 2;
      context.fillText(this.human.name, this.centerPosX-adjustPos, this.centerPosY+this.radius*2.2); 
      context.stroke();
    }
  }
  private strokeBirthAndDeath(context: CanvasRenderingContext2D) {
    let text : string | undefined = undefined;
    if (this.human.dateOfBirth != undefined)
    {
      text = this.human.dateOfBirth.slice(0,4);
    }
    if (this.human.dateOfDeath != undefined)
    {
      if (this.human.dateOfBirth === undefined)
      {
        text = "\u271D" + this.human.dateOfDeath.slice(0,4);
      }
      else
      {
        text += "-" + this.human.dateOfDeath.slice(0,4);
      }
    }
    if (text !== undefined)
    {
      context.font = "0.8em serif";
      let adjustPos = context.measureText(text).width / 2;
      context.fillText(text, this.centerPosX-adjustPos, this.centerPosY-this.radius*1.5); 
      context.stroke();
    }
  }
}
  