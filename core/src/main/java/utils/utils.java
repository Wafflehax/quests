package utils;

import java.util.Stack;

public class utils{
	
		public static Stack<IntPair> getMaxIntPairs(Stack<IntPair> p){
			//get Maximum of value1's
			if(p != null && p.size() > 0) {
				Stack<IntPair> r = new Stack<IntPair>();
				int max = p.get(0).value1;
				for(int i = 0; i < p.size(); i++) {
					if(p.get(i).value1 > max) {
						max = p.get(i).value1;
						r.clear();
						r.add(p.get(i));
					}else if(p.get(i).value1 == max) {
						r.add(p.get(i));
					}
				}
				return r;
			}else
				return p;
		}	
}