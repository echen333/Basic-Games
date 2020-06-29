import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Slide_Puzzle extends PApplet {

int gameState=0;
int board[][]=new int[4][4];
int margin=width/5;
int ei=0;//empty cell y
int ej=0;//empty cell x
int startTime=0;
int stopTime=-1;
int count=0;
boolean started=false;
public void setup() {
  
  background(255);
  stroke(255);
  shuffleBoard();
  textAlign(CENTER);
}
public void draw() {
  background(255);
  if (!started)startTime=millis();
  text("new",2*width/3,margin);
  println(count);
  if (gameState==0) {
    textSize(20);
    for (int i=0; i<4; i++) {
      for (int j=0; j<4; j++) {
        if (board[i][j]!=0) {
          fill(90, 193, 142);
          rect(margin+i*(width-2*margin)/4, 2*margin+j*(width-2*margin)/4, (width-2*margin)/4, (width-2*margin)/4, width/30, width/30, width/30, width/30);
          fill(0, 0, 0);
          text(board[i][j], margin+i*(width-2*margin)/4+(width-2*margin)/8, 2*margin+j*(width-2*margin)/4+(width-2*margin)/8);
        } else {
          ei=i;
          ej=j;
          fill(255);
          rect(margin+i*(width-2*margin)/4, 2*margin+j*(width-2*margin)/4, (width-2*margin)/4, (width-2*margin)/4);
        }
      }
    }
  } else {
    rectMode(CENTER);
    textAlign(CENTER);
    text("Congrats, you solved the puzzle in "+(stopTime-startTime)/1000+" seconds", (float)width/2, (float)width/2, width-2*margin, width-2*margin);
  }
  textSize(20);
  fill(0);
  if (stopTime==-1) {
    text((millis()-startTime)/1000, margin, margin);
  } else {
    text((stopTime-startTime)/1000, margin, margin);
  }
}

public void mousePressed() {
  int mi=(mouseX-margin)/((width-2*margin)/4);
  int mj=(mouseY-margin)/((width-2*margin)/4);
  if(mouseX>width/2 && mouseY<width/10){
    shuffleBoard();
    started=false;
    startTime=millis();
    gameState=0;
    rectMode(CORNER);
    stopTime=-1;
    return;
  }
  if ((ei==mi||ej==mj)&&mouseX>margin &&mouseY>margin) {
    if (ei==mi) {
      if (ej<mj) {
        for (int i=ej; i<mj; i++) {
          board[ei][i]=board[ei][i+1];
        }
      } else {
        for (int i=ej; i>mj; i--) {
          board[ei][i]=board[ei][i-1];
        }
      }
      board[ei][mj]=0;
    } else {
      if (ei<mi) {
        for (int i=ei; i<mi; i++) {
          board[i][ej]=board[i+1][ej];
        }
      } else {
        for (int i=ei; i>mi; i--) {
          board[i][ej]=board[i-1][ej];
        }
      }
      board[mi][ej]=0;
    }
    isSolved();
    if (!started) {
        stopTime=-1;
        started=true;
    }
  }
}
public void keyPressed() {
  if (key==CODED) {
    int[][] move={{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    int change=-1;
    if (keyCode==LEFT && ei!=3)change=0;
    if (keyCode==RIGHT && ei!=0)change=1;
    if (keyCode==UP && ej!=3)change=2;
    if (keyCode==DOWN && ej!=0)change=3;
    if (change!=-1) {
      board[ei][ej]=board[ei+move[change][0]][ej+move[change][1]];
      board[ei+move[change][0]][ej+move[change][1]]=0;
      if (board[3][3]==0)isSolved();
      if (!started) {
        stopTime=-1;
        started=true;
      }
      count++;
    }
  }
}
public void isSolved() {
  for (int i=0; i<15; i++) {
    if (board[(i)%4][(i)/4]!=i+1) {
      return;
    }
  }
  gameState=1;
  stopTime=millis();
}
public void shuffleBoard() {
  for (int i=0; i<4; i++) {
    for (int j=0; j<4; j++) {
      board[i][j]=4*i+j;
    }
  }
  board[0][0]=15;
  board[3][3]=0;
  for (int i=0; i<15; i++) {
    int rand=(int)random(15);
    int temp=board[i/4][i%4];
    board[i/4][i%4]=board[rand/4][rand%4];
    board[rand/4][rand%4]=temp;
  }
  //its only solvable if number of inversions is even
  if (getInversions()%2==1) {
    int temp=board[0][0];
    board[0][0]=board[0][1];
    board[0][1]=temp;
  }
  started=false;
}
public int getInversions() {
  int count=0;
  for (int i=0; i<15; i++) {//doesnt go over lasy square bc empty
    for (int j=i+1; j<15; j++) {
      if (board[i/4][i%4]>board[j/4][j%4]) {
        count++;
      }
    }
  }
  return count;
}
  public void settings() {  size(300, 300); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#666666", "--stop-color=#cccccc", "Slide_Puzzle" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
