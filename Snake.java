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

public class Snake extends PApplet {

int gridSz=30;
int board[][]=new int[gridSz][gridSz];
int ax;
int ay;
ArrayList<Integer> r=new ArrayList<Integer>();
ArrayList<Integer> c=new ArrayList<Integer>();
int dir=1;
int dr[]={-1, 1, 0, 0};
int dc[]={0, 0, -1, 1};
boolean inGame=true;
boolean dirUsed=true;
ArrayList<Integer> moves=new ArrayList<Integer>();
public void setup() {
  
  background(255);
  r.add(gridSz/2);
  c.add(gridSz/2);
  generateNewApple();
}
public void draw() {
  if (inGame) {
    for (int i=0; i<gridSz; i++) {
      line(0, width/gridSz*i, width, width/gridSz*i);
      line(width/gridSz*i, 0, width/gridSz*i, width);
    }
    for (int i=0; i<gridSz; i++) {
      for (int j=0; j<gridSz; j++) {
        board[i][j]=0;
      }
    }
    board[ax][ay]=2;
    for (int i=0; i<r.size(); i++) {
      board[r.get(i)][c.get(i)]=1;
    }
    for (int i=0; i<r.size(); i++) {
      for (int j=i+1; j<r.size(); j++) {
        if (r.get(i)==r.get(j) && c.get(i)==c.get(j)) {
          inGame=false;
        }
      }
    }
    for (int i=0; i<gridSz; i++) {
      for (int j=0; j<gridSz; j++) {
        if (board[i][j]==0) {
          fill(0);
        } else if (board[i][j]==1) {
          fill(0, 255, 0);
        } else {
          fill(255, 0, 0);
        }
        rect(width/gridSz*i, width/gridSz*j, width/gridSz*(i+1), width/gridSz*(j+1));
      }
    }
    if (frameCount%3==0) {
      if (!moves.isEmpty()) {
        dir=moves.get(0);
        moves.remove(0);
      }
      if (r.get(0)==ax && c.get(0)==ay) {
        r.add(0, r.get(0)+dr[dir]);
        c.add(0, c.get(0)+dc[dir]);
        generateNewApple();
      } else {
        for (int i=r.size()-1; i>=1; i--) {
          r.set(i, r.get(i-1));
          c.set(i, c.get(i-1));
        }
        r.set(0, r.get(0)+dr[dir]);
        c.set(0, c.get(0)+dc[dir]);
        if (r.get(0)==-1 || r.get(0)==gridSz ||c.get(0)==-1 || c.get(0)==gridSz) {
          inGame=false;
        }
      }
    }
  }
}
public void generateNewApple() {
  ax=(int)random(gridSz);
  ay=(int)random(gridSz);
  for (int i=0; i<r.size(); i++) {
    if (r.get(i)==ax && c.get(i)==ay) {
      generateNewApple();
      break;
    }
  }
}
public void keyPressed() {
  int x=0;
  if (!moves.isEmpty()) {
    x=moves.get(moves.size()-1);
  } else {
    x=dir;
  }
  if (keyCode==LEFT && x!=1) {
    moves.add(0);
  }
  if (keyCode==RIGHT && x!=0) {
    moves.add(1);
  }
  if (keyCode==UP && x!=3) {
    moves.add(2);
  }
  if (keyCode==DOWN && x!=2) {
    moves.add(3);
  }
}
  public void settings() {  size(600, 600); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#666666", "--stop-color=#cccccc", "Snake" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
