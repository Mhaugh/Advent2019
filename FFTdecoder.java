package advent;

import java.util.ArrayList;

public class FFTdecoder {
	static String decode(String in, int operations, int repititions){
		String input = "";
		for(int r = 0; r < repititions; r++){
			input += in;
		}
		ArrayList<Integer> currentset = new ArrayList<Integer>();
		for(int i = 0; i < input.length(); i++) {
			 currentset.add(Character.getNumericValue(input.charAt(i)));
		}
		for(int n = 1; n <= operations; n++){
			currentset = getnextset(currentset);
		}
		String out = "";
		for(Integer o: currentset) {
			out += o.toString();
		}
		return out;
	}

	static private ArrayList<Integer> getnextset(ArrayList<Integer> currentset) {
		ArrayList<Integer> nextset = new ArrayList<Integer>();
		for(int t = 0; t < currentset.size(); t++){
			ArrayList<Integer> pattern = getnextpattern(t+1, currentset.size()+1);
			Integer runner = 0;
			for(int i = 0; i < currentset.size(); i ++) {
				runner += (currentset.get(i) * pattern.get(i));
			}
			String out = runner.toString();
			nextset.add(Character.getNumericValue(out.charAt(out.length()-1)));
		}
		return nextset;
	}
	static private ArrayList<Integer> getnextpattern(int i, int size){
		ArrayList<Integer> nextPattern = new  ArrayList<Integer>();	
		int[] patternTemplate = {0, 1, 0, -1};
		for(int a = 0; a <= size/(i*4); a++){
			for(int g: patternTemplate){
				for(int n = 0; n < i; n++){
					nextPattern.add(new Integer(g));
				}
			}
		}
		nextPattern.remove(0);
		return nextPattern;
	}
}
