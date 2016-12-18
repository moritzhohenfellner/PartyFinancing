package util;

import java.util.Random;

public class GlobalRandom {
	//Holds global random generator to be able to controll for seeds 
	public static Random rand;
	public static long seed;
	public GlobalRandom() {
		// TODO Auto-generated constructor stub
		
		
	}
	public void start(long seed){
		this.seed=seed;
		this.rand = new Random(seed);
	}

}
