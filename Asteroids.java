package advent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Asteroids {
	
	
	static List<Point> mapAsteroids(String input){
		List<Point> output = new ArrayList<>();
		for(int y = 0; y<33; y++) {
			for(int x = 0; x<33; x++) {
				if('#'==input.charAt(x+(y*33))) {
					output.add(new Point(x,y));
				}
			}
		}
	return output;
	}
	
	static Point doPart1(List<Point> input){
		Point bestPoint = new Point(-1,-1);
		int highscore = 0;
		for(Point A: input){
			int currentscore = 0;
			for(Point B: input){
				if(!checkIfPointBlocked(A, B, input)){
					currentscore++;
				}
			}
			if(currentscore>highscore){
				bestPoint = A;
				highscore=currentscore;
			}
		}
		System.out.println("best score: "+highscore+" x: "+bestPoint.getX()+" y: "+bestPoint.getY());
		return bestPoint;
	}
	static void doPart2(List<Point>detected, Point laserPos){
		List<Point> detectableAstroids = detected.subList(0,detected.size());
		List<Point> detectedAstroids = detectableAstroids.stream().sorted(Comparator.comparingDouble(a -> angleOf(laserPos, a))).collect(Collectors.toList());
		int target = 0;
		for(Point p: detectedAstroids){
			System.out.println(p.getX()+""+p.getY());
			if(!checkIfPointBlocked(p, laserPos, detected)) {
				target++;
			}
			if(target==220) {
				break;
			}
		}
	}
	
	public static double angleOf(Point center, Point to) {
        double radians = Math.atan2((to.getY() - center.getY()) * 1.0, (to.getX() - center.getY()) * 1.0);
        radians = (radians + 2 * Math.PI) % (2 * Math.PI); // Make positive
        return (radians + Math.PI / 2) % (2 * Math.PI); // Translate to our coords
    }
	

	static boolean checkIfPointBlocked(Point A, Point B, List<Point> points) {
		int xa = A.getX();
		int ya = A.getY();
		int xb = B.getX();
		int yb = B.getY();
		if (xa == xb&&ya==yb) {
			return true;//handles a point trying to see itself, 
		}
		if(xa<=xb) {
			int xdist = (xb-xa);
			int ydist = (yb-ya);
			if(xa==xb){
				if(ya<yb) {
					for(int y = ya+1; y<yb; y++) {
						for(Point p: points){
							if(y == p.getY()&&xa==p.getX()) return true;
						}
					}
				}else {
					for(int y = yb+1; y<ya; y++) {
						for(Point p: points){
							if(y == p.getY()&&xa==p.getX()) return true;
						}
					}
				}
			}else if(ydist == 0){					
				for(int x = xa+1; x<xb;x++) {
					for(Point p: points){
						if(x == p.getX()&&ya==p.getY()) return true;
					}
				}
			}else { // co-factor with x&&y %n == 0 will help find the points
				for(int n=32; n>0 ;n--) {
					if(ydist%n==0&&xdist%n==0){
						for(int c=1;c<n;c++){//cofactor 0 and N aren't consider as they are the two points
							for(Point p: points){
								if(xa+(c*(xdist/n)) == p.getX()&&ya+(c*(ydist/n))==p.getY()) return true;
							}
						}
					}
				}
			}
		}else{//b leftmost point
			int xdist = (xa-xb);
			int ydist = (ya-yb);
			if(ya==yb){//horizontal equity alredy checked	
				for(int x = xb+1; x<xa;x++) {
					for(Point p: points){
						if(x == p.getX()&&ya==p.getY()) return true;
					}
				}
			}else { // co-factor with x&&y %n == 0 will help find the points
				for(int n=32; n>0 ;n--) {
					if(ydist%n==0&&xdist%n==0){
						for(int c=1;c<n;c++){//cofactor 0 and N aren't consider as they are the two points
							for(Point p: points){
								if(xb+(c*(xdist/n)) == p.getX()&&yb+(c*(ydist/n))==p.getY()) return true;
							}
						}
					}
				}
			}
		}
		return false;
	}
}
