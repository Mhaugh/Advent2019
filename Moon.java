package advent;



public class Moon {
	private int xPos;
	private int yPos;
	private int zPos;
	private int[] velocity;
	
	Moon(int x, int y, int z){
		xPos = x;
		yPos = y;
		zPos = z;
		velocity = new int [] {0,0,0};
	}
	
	public void gravityCalc(Moon otherMoon){
		if(otherMoon.getXPos()>getXPos()) {
			velocity[0]++;
		}else if(otherMoon.getXPos()<getXPos()){
			velocity[0]--;
		}
		if(otherMoon.getYPos()>getYPos()) {
			velocity[1]++;
		}else if(otherMoon.getYPos()<getYPos()){
			velocity[1]--;
		}
		if(otherMoon.getZPos()>getZPos()) {
			velocity[2]++;
		}else if(otherMoon.getZPos()<getZPos()){
			velocity[2]--;
		}
	}
	public void timeStep() {
		xPos += velocity[0];
		yPos += velocity[1];
		zPos += velocity[2];
	}
	
	public int getXPos(){
		return xPos;
	}
	public int getYPos(){
		return yPos;
	}
	public int getZPos(){
		return zPos;
	}
	public int[] getVelocity() {
			int[] out =  velocity;
		return out;
	}
	public int getTotalEnergy() {
		int out = 0;
		int out2 = 0;
		out += Math.abs(getXPos());
		out += Math.abs(getYPos());
		out += Math.abs(getZPos());
		for(int i:velocity) {
			out2 += Math.abs(i);
		}
		return out*out2;
	};
}

/*	
	public static void main(String[] args){
		ArrayList<Moon> moons = new ArrayList<Moon>();
		moons.add(new Moon(3, 15, 8));
		moons.add(new Moon(5, -1, -2));
		moons.add(new Moon(-10, 8, 2));
		moons.add(new Moon(8,4,-5));
		boolean run = true;
		long time = 0;
		long xCycle  = 0;
		long yCycle = 0;
		long zCycle = 0;
		int[][] initalState = new int[4][6];
		for(int i=0; i<4;i++) {
			initalState[i][0] = moons.get(i).getXPos();
			initalState[i][1] = moons.get(i).getYPos();
			initalState[i][2] = moons.get(i).getZPos();
			for(int n = 0; n<3; n++) {
				initalState[i][n+3] = moons.get(i).getVelocity()[n];
			}
		}
		while(run) {
			for(Moon m: moons) {
				for(Moon o: moons) {
					if(!m.equals(o)) {//Technically dosn't change anything because equal position dosn't change velocity
						m.gravityCalc(o);
					}
				}
			}
			for(Moon m: moons) {
				m.timeStep();
			}
			time++;
			if(xCycle==0){
				int match = 0;
				for(int i=0; i<4;i++) {
					if(initalState[i][0] == moons.get(i).getXPos()&&initalState[i][3] == moons.get(i).getVelocity()[0]) {
						match++;
					}
				}
				if(match == 4) {
					xCycle = time;
				}
			}
			if(yCycle==0){
				int match = 0;
				for(int i=0; i<4;i++) {
					if(initalState[i][1] == moons.get(i).getYPos()&&initalState[i][4] == moons.get(i).getVelocity()[1]) {
						match++;
					}
				}
				if(match == 4) {
					yCycle = time;
				}
			}
			if(zCycle==0){
				int match = 0;
				for(int i=0; i<4;i++) {
					if(initalState[i][2] == moons.get(i).getZPos()&&initalState[i][5] == moons.get(i).getVelocity()[2]) {
						match++;
					}
				}
				if(match == 4) {
					zCycle = time;
				}
			}
			if(!(zCycle==0) && !(xCycle==0) && !(yCycle==0)){
				run = false;
			}
		}
		
		System.out.println(zCycle*xCycle*yCycle);
		System.out.println(zCycle+" "+xCycle+" "+yCycle);
		//get less lazy and do your prime factors!
		
		
	/*<x=3, y=15, z=8>
	<x=5, y=-1, z=-2>
	<x=-10, y=8, z=2>
	<x=8, y=4, z=-5>
	*/
		/* part 1
		ArrayList<Moon> moons = new ArrayList<Moon>();
		 
		moons.add(new Moon(3, 15, 8));
		moons.add(new Moon(5, -1, -2));
		moons.add(new Moon(-10, 8, 2));
		moons.add(new Moon(8,4,-5));
		for(int t=0; t<1000; t++){
			for(Moon m: moons) {
				for(Moon o: moons) {
					if(!m.equals(o)) {//Technically dosn't change anything because equal position dosnt change velocity
						m.gravityCalc(o);
					}
				}
			}
			for(Moon m: moons) {
				m.timeStep();
			}
		}
		int out = 0;
		for(Moon m: moons) {
			out += m.getTotalEnergy();
		}
		System.out.println(out);
		
	}
*/
