package utils;

import java.util.Stack;

public class utils{
	
		public static Stack<IntPlayerPair> getMaxIntPairs(Stack<IntPlayerPair> p){
			//get Maximum of value1's
			if(p != null && p.size() > 0) {
				Stack<IntPlayerPair> r = new Stack<IntPlayerPair>();
				int max = p.get(0).value;
				for(int i = 0; i < p.size(); i++) {
					if(p.get(i).value > max) {
						max = p.get(i).value;
						r.clear();
						r.add(p.get(i));
					}else if(p.get(i).value == max) {
						r.add(p.get(i));
					}
				}
				return r;
			}else
				return p;
		}	
		
		
		
		public static void sleep(int milisecs) {
			try {
				Thread.sleep(milisecs); // sleeping to wait for game initialization
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}	
}