package advent;

import java.util.ArrayList;

public class Frames {
	public static void draw(String input){
		ArrayList<int[][]> frames = new ArrayList<int[][]>();
		for(int f = 0; f<input.length()/150; f++) {
			int[][] frame = new int[6][25];
			for(int l=0; l<6; l++) {
					for(int i=0; i<25; i++) {
						frame[l][i] = Integer.parseInt(input.substring(i+(f*150)+(l*25), i+(f*150)+(l*25)+1));
					}
			}
			frames.add(frame);
		}
		int fewestZeros = 151;
		int out = 30000000;
		for(int frame = 0; frame<frames.size(); frame++){
			int[][] f = frames.get(frame);
			int zeros = 0;
			int ones = 0;
			int twos = 0;
			for(int l=0; l<6; l++) {
				for(int i=0; i<25; i++) {	
					if(f[l][i] == 0) {
						zeros++;
					}else if(f[l][i] == 1) {
						ones++;
					}else if(f[l][i] == 2) {
						twos++;
					}
				}
			}
			if(zeros<fewestZeros) {
				out = ones*twos;
				fewestZeros = zeros;
			}
			
		}
		System.out.println(out);
		char[][] outframe = new char[6][25];
		for(int l=0; l<6; l++) {
			for(int i=0; i<25; i++) {	
				outframe[l][i]=compressLayers(frames, l, i);
			}
		}
		for(char[] line: outframe) {
			for(char i: line) {
				System.out.print(i);
			}
			System.out.println();
		}
	}


	static private char compressLayers(ArrayList<int[][]> input, int y, int x) {
		for(int frame = 0; frame<input.size(); frame++){
			int[][] temp = input.get(frame);
			if(temp[y][x]==1) {
				return 'O';
			}
			if(temp[y][x]==0) {
				return '_';
			}
		}
		return 'U';
	}
}
