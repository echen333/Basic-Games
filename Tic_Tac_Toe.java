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

public class Tic_Tac_Toe extends PApplet {

int[][] board=new int[3][3];
int cols = 3;  
int rows = 3;  
boolean turn=true;
public void setup() {   
  
  background(255);  
  line(width/3,0,width/3,width);
  line(2*width/3,0,2*width/3,width);
  line(0,width/3,width,width/3);
  line(0,2*width/3,width,2*width/3);
  board[2][2]=1;
}   

public void draw() {  
  for (int i = 0; i < cols; i++) {
    for (int j = 0; j < rows; j++) {
      if(board[i][j]==1){
        line(width/12+width/3*i,width/12+width/3*j,width/4+width/3*i,width/4+width/3*j);
        line(width/4+width/3*i,width/12+width/3*j,width/12+width/3*i,width/4+width/3*j);
      }
      if(board[i][j]==-1){
        circle(width/6+width/3*i,width/6+width/3*j,width/6);
      }
    }
  }
}   

public void mousePressed() {   
  if (turn) {
    int i=mouseX/(width/3);
    int j=mouseY/(width/3);
    if(board[i][j]==0){
      board[i][j]=-1;
      turn=false;
      float max=-102;
      int ni=-1;
      int nj=-1;
      int[][]board2=new int[3][3];
      for(int a=0;a<3;a++){
        for(int b=0;b<3;b++){
          board2[a][b]=board[a][b];
        }
      }
      boolean stop=false;
      for(int a=0;a<3;a++){
        for(int b=0;b<3;b++){
          if(board[a][b]==0){
            board2[a][b]=1;
            float score=score(board2,false);
            println(score+" "+a+" "+b);
            if(score>max){
              max=score;
              ni=a;
              nj=b;
            }
            if(isWon(board2)){
              ni=a;
              nj=b;
              stop=true;
              break;
            }
            board2[a][b]=0;
          }
        }
        if(stop)break;
      }
      //println("next"+ni+" "+nj);
      board[ni][nj]=1;
      turn=true;
    }
  }
}   

public float score(int board[][],boolean isAI){
  if(isLost(board) && isAI)return -100;
  if(isLost(board) && !isAI)return 100;
  float sum=0;
  float count=0;
  for(int i=0;i<3;i++){
    for(int j=0;j<3;j++){
      if(board[i][j]==0){
        if(isAI && isLost(change(board,i,j)))return -101;
        if(isAI){
           sum=Math.max(sum,score(change(board,i,j),!isAI)); 
        }
        else{
          sum=Math.min(sum,score(change(board,i,j),!isAI));
        }
        count++;
      }
    }
  }
  if(count==0)return 0;//draw
  return sum;
}

public boolean isLost(int board[][]){
  if(board[0][0]==-1 && board[0][1]==-1 && board[0][2]==-1)return true;
  if(board[1][0]==-1 && board[1][1]==-1 && board[1][2]==-1)return true;
  if(board[2][0]==-1 && board[2][1]==-1 && board[2][2]==-1)return true;
  if(board[0][0]==-1 && board[1][0]==-1 && board[2][0]==-1)return true;
  if(board[0][1]==-1 && board[1][1]==-1 && board[2][1]==-1)return true;
  if(board[0][2]==-1 && board[1][2]==-1 && board[2][2]==-1)return true;
  if(board[0][0]==-1 && board[1][1]==-1 && board[2][2]==-1)return true;
  if(board[0][2]==-1 && board[1][1]==-1 && board[2][0]==-1)return true;
  return false;
}
public boolean isWon(int board[][]){
  if(board[0][0]==1 && board[0][1]==1 && board[0][2]==1)return true;
  if(board[1][0]==1 && board[1][1]==1 && board[1][2]==1)return true;
  if(board[2][0]==1 && board[2][1]==1 && board[2][2]==1)return true;
  if(board[0][0]==1 && board[1][0]==1 && board[2][0]==1)return true;
  if(board[0][1]==1 && board[1][1]==1 && board[2][1]==1)return true;
  if(board[0][2]==1 && board[1][2]==1 && board[2][2]==1)return true;
  if(board[0][0]==1 && board[1][1]==1 && board[2][2]==1)return true;
  if(board[0][2]==1 && board[1][1]==1 && board[2][0]==1)return true;
  return false;
}

public int[][] change(int board[][], int a, int b){
  int ret[][]=new int[3][3];
  for(int i=0;i<3;i++){
    for(int j=0;j<3;j++){
      ret[i][j]=-1*board[i][j];
    }
  }
  if(board[a][b]==0){
    ret[a][b]=1;
  }
  return ret;
}
class Board{
  int[][] board;
  Board(){
    board=new int[3][3];
  }
  
}
  public void settings() {  size(300, 300); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Tic_Tac_Toe" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
