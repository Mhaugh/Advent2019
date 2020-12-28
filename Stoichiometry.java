package advent;

import java.util.ArrayList;
import java.util.HashMap;

public class Stoichiometry {
	static HashMap<String, Reaction> ReactionsList = new HashMap<String, Reaction>();
	static HashMap<String, Integer> Leftovers = new HashMap<String, Integer>();
	static public long CalculateFinalReactions(String input) {
		String[] inputArray = input.split("\r\n");
		for(String s: inputArray) {
			Reaction currentReaction = new Reaction(s);
			ReactionsList.put(currentReaction.getName(), currentReaction);
		}
		long n = 0;
		long totalore = 0;
		boolean b = true;
		while(b) {
			totalore = totalore + ReactionsList.get("FUEL").getTotalOre();
			if(totalore > 1000000000000L) {
				b = false;
				continue;
			}
			n++;
			System.out.println(n);
			System.out.println(totalore);
			System.out.println(1000000000000L);
		}
		return n;
	}
}

class Reaction {
	String name;
	Integer amountCreated;
	ArrayList<String> reagents = new ArrayList<String>();
	HashMap<String, Integer> reagentAmounts = new HashMap<String, Integer>();
	Reaction(String input){
		String[] inputArray = input.split(" => ");
		String[] resultData = inputArray[1].split(" ");
		name = resultData[1];
		amountCreated = new Integer(resultData[0]);
		String[] reactionArray = inputArray[0].split(", ");
		for(String s: reactionArray){
			String[] reagent = s.split(" ");
			reagentAmounts.put(reagent[1], new Integer(reagent[0]));
			reagents.add(reagent[1]);
		}
	}
	int getTotalOre(){
		int total = 0;
		for(String s: reagents) {
			if(s.equalsIgnoreCase("Ore")) {
				total += reagentAmounts.get(s);
			}else{
				boolean volumeToLow = true;
				int n = 0;
				do{
					Reaction neededReaction = Stoichiometry.ReactionsList.get(s);
					if(Stoichiometry.Leftovers.containsKey(s)) {
						if((neededReaction.amountCreated * n) + Stoichiometry.Leftovers.get(s)  >= reagentAmounts.get(s)) {
							Stoichiometry.Leftovers.put(s, new Integer((neededReaction.amountCreated * n) + Stoichiometry.Leftovers.get(s))  - reagentAmounts.get(s));
							for(int i = 0; i<n;i++) {
								total += neededReaction.getTotalOre();
							}
							volumeToLow = false;  
						}
					}else {
						if(neededReaction.amountCreated * n  >= reagentAmounts.get(s)) {
							if((neededReaction.amountCreated * n)  - reagentAmounts.get(s) > 0) {
								Stoichiometry.Leftovers.put(s, new Integer(neededReaction.amountCreated * n)  - reagentAmounts.get(s));
							}
							for(int i = 0; i<n; i++) {
								total += neededReaction.getTotalOre();
							}
							volumeToLow = false;
						}
					}
					n++;
				}while(volumeToLow);
			}
		}
		return total;
	}
	public String getName() {
		return name;
	}
}