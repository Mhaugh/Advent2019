package advent;

import java.util.ArrayList;

public class Computer {
	private boolean halt = false;
	private int currentIndex = 0;
	private long indexIterate = 4;
	private long baseoffset = 0;
	private ArrayList<Long> Program;
	private ArrayList<Long> outBuffer = new ArrayList<Long>();
	
	Computer(String inputProgram){
		Program = new ArrayList<Long>(100000000);
		
		String[] inputholder = inputProgram.split(",");
		//breaks the inputProgram String into a usable format
		for(int i = 0; i<=100000000; i++){
			Program.add(i, (long) 0);
		}
		for(int i = 0; i < inputholder.length ; i++) {
			Program.set(i, Long.parseLong(inputholder[i]));
		}
	}
	
	Computer(ArrayList<Long> inProgram, int Index){
		currentIndex = Index;
		Program = new ArrayList<Long>(100000000);
		
		//breaks the inputProgram String into a usable format
		for(int i = 0; i<=100000000; i++){
			Program.add(i, (long) 0);
		}
		for(int i = 0; i < Program.size(); i++) {
			Program.set(i, inProgram.get(i));
		}
	}
	
	void run() throws NumberFormatException{
		
		while(!halt) {
			long value1;
			long value2;
			int target;
			String paramMode= "0000000"+Program.get(currentIndex);
			int opcode = Integer.parseInt(paramMode.substring(paramMode.length()-2));
			//System.out.println("now: "+currentIndex+" "+paramMode);
			switch(opcode) {			//tells program to add
			case 1: 
				value1 = setMode(paramMode, 1);
				value2 = setMode(paramMode, 2);
				target = setTarget(paramMode, 3);
				
				Program.set(target, new Long(value1+value2)); 
				
				indexIterate = 4;
				break;
			case 2:	//tells program to multiply
				value1 = setMode(paramMode, 1);
				value2 = setMode(paramMode, 2);
				target = setTarget(paramMode, 3);
				
				Program.set(target, new Long(value1*value2));
				
				indexIterate = 4;
			 	break;
			case 3:	//tells program to take input
				return;
				/*
				target = Program.get(currentIndex+1);
				Program.set(target, inputs[currentInput]); //make sure to take proper inputs from the needed input source
				indexIterate = 2;
			 	break;
			 	*/
			case 4:	//tells program to set output
				value1 = setMode(paramMode, 1);

				outBuffer.add(value1);
				//System.out.println(value1);
			 	indexIterate = 2;
			 	break;
			case 5:
				value1 = setMode(paramMode, 1);
				value2 = setMode(paramMode, 2);
				
				if(!(value1 == 0)){
					currentIndex = new Long(value2).intValue();
					indexIterate = 0;
					break;
				}
				
				indexIterate = 3;
				break;
			case 6:
				value1 = setMode(paramMode, 1);
				value2 = setMode(paramMode, 2);
				
				if((value1 == 0)){
					currentIndex = new Long(value2).intValue();
					indexIterate = 0;
					break;
				}
				
				indexIterate = 3;
				break;
			case 7:
				value1 = setMode(paramMode, 1);
				value2 = setMode(paramMode, 2);
				target = setTarget(paramMode, 3);
				
				if((value1 < value2)){
					Program.set(target, (long) 1);
				}else{
					Program.set(target, (long) 0);
				}
				
				indexIterate = 4;
				break;
			case 8:
				value1 = setMode(paramMode, 1);
				value2 = setMode(paramMode, 2);
				target = setTarget(paramMode, 3);
				
				if((value1 == value2)){
					Program.set(target, (long) 1);
				}else{
					Program.set(target, (long) 0);
				}
				
				indexIterate = 4;
				break;
			case 9:
				value1 = setMode(paramMode, 1);
				baseoffset = baseoffset + value1;
				indexIterate = 2;
				break;
			case 99:    //ends the program 'cleanly'
				halt = true;
				break;
			default://tells you about the mistake in your code, or that the combination never reaches the end state, probably should throw an error
				System.out.print("Operator invalid: " + currentIndex + ", value index 0: "+Program.get(0).intValue());
				halt = true;  //if you got an opcode wrong you don't know what your doing and your results are garbage anyway
			}
			currentIndex+=indexIterate;
		}
		return;
	}
	
	private Long setMode(String paramMode, int offset){
		switch(paramMode.charAt(paramMode.length()-(offset+2))) {
		case '0':
			return Program.get(Program.get(currentIndex+offset).intValue());
		case '1':
			return Program.get(currentIndex+offset);
		case '2':
			return Program.get(new Long(Program.get(new Long(currentIndex+offset).intValue())+baseoffset).intValue());
		default:
			System.out.println("Bad opcode mode switch: "+paramMode+" at "+ (currentIndex+offset));
			halt = true;
			return (long) 0;
		}
	}
	
	private int setTarget(String paramMode, int offset){
		switch(paramMode.charAt(paramMode.length()-(offset+2))) {
		case '0':
			return Program.get(currentIndex+offset).intValue();
		case '2':
			Long n = new Long(Program.get(currentIndex+offset)+baseoffset);
			return n.intValue();
		case '1':
		default:
			System.out.println("Bad opcode mode switch: "+paramMode+" at "+ (currentIndex+offset));
			halt = true;
			return 0;
		}
	}
	
	void setInput(long currentInput){
		int target = new Long(setTarget(("000"+Program.get(currentIndex)), 1)).intValue();
		Program.set(target, currentInput);
		currentIndex+=2;
		outBuffer = new ArrayList<Long>();//flushes the buffer
		try {
			run();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}
	
	ArrayList<Long> getOutput() {
		ArrayList<Long> out = new ArrayList<Long>();
		for(Long l: outBuffer){
			Long n =new Long(l);
			out.add(n);
		}
		return out;
	}
	
	boolean isHalt() {
		return halt;
	}
	
	int getCurrentIndex() {
		return currentIndex;
	}
	
	ArrayList<Long> getProgram(){
		ArrayList<Long> output = new ArrayList<Long>();
		for(Long l: Program){
			Long i = new Long(l); 
			output.add(i);
		}
		
		return output;
	}
}

