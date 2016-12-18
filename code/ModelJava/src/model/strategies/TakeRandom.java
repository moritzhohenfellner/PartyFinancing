package model.strategies;

import util.GlobalRandom;
import network.Agent;
import model.MassMediaCarlos;
import model.Strategy;

public class TakeRandom extends Strategy {

	public TakeRandom() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public Agent selectNextAgent(int ts){
		int count = this.carlos.network.availableAgentsInTS.size();
		int index = GlobalRandom.rand.nextInt(count);
		
		
		return this.carlos.network.availableAgentsInTS.get(index);
	}

}
