package utils;

import java.util.Stack;

public class utils{
	
	//delete this method
		public static Stack<IntPair> getMaxIntPairs(Stack<IntPair> p){
			// inefficent
			Stack<IntPair> r = new Stack<IntPair>();
			int max = p.get(0).value;
			for(int i = 0; i < p.size(); i++) {
				if(p.get(i).value > max) {
					max = p.get(i).value;
				}
			}
			for(int i = 0; i < p.size(); i++) {
				if(p.get(i).value == max) {
					r.add(p.get(i));
				}
			}
			return r;
		}	
}