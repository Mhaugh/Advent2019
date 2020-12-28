package advent;

import java.util.Random;

public class Mazerunner {
	private long[][] maze;
	private int currentX;
	private int currentY;
	Computer c;
	Mazerunner(){
		maze = new long[81][81];
		for(int y = 0; y<81; y++){
			for(int x = 0; x<81; x++){
				maze[y][x] = 3l; //sets an unused value to all maze positions
			}
		}
	}
	
	void start(String interCode){ //start point set to known as open
		Random r = new Random();
		c = new Computer(interCode);
		c.run();
		runMaze(c, 40, 40, r);
	
		for(int v = 0; v<81; v++) {
			System.out.println();
			for(int m = 0; m<81; m++){
				System.out.print(maze[v][m]);
			}
		}
	}
	
	private void runMaze(Computer c, int y, int x, Random n){
		boolean exitFound = false;
		currentX = x;
		currentY = y;
		while(!((exitFound)&&!(currentX==x)&&!(currentY==y))){
			if(maze[currentY][currentX]==2) {
				exitFound=true;
			}
			picknext(currentX, currentY, n);
		}
		for(int t = 0; t<10000000; t++) {
			picknext(currentX, currentY, n);
		}
		maze[y][x]=4;
	}
	
	private void picknext(int x, int y, Random r){
		int d = r.nextInt(4);
		switch(d) {
		case 0:
			c.setInput(1);
			maze[y+1][x] = c.getOutput().get(0);
			if(c.getOutput().get(0)!=0){
				currentY+=1;
			}
			return;
		case 1:
			c.setInput(2);
			maze[y-1][x] = c.getOutput().get(0);
			if(c.getOutput().get(0)!=0){
				currentY-=1;
			}
			return;
		case 2:
			c.setInput(3);
			maze[y][x-1] = c.getOutput().get(0);
			if(c.getOutput().get(0)!=0){
				currentX-=1;
			}
			return;
		case 3:
			c.setInput(4);
			maze[y][x+1] = c.getOutput().get(0);
			if(c.getOutput().get(0)!=0){
				currentX+=1;
			}
			return;
		}
	}
}
