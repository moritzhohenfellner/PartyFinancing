package model.strategies;

import java.util.LinkedList;

import network.Agent;
import model.Strategy;

public class TakeConvincible extends Strategy {

	public TakeConvincible() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Agent selectNextAgent(int ts){
		LinkedList<Agent> list;
		list=this.carlos.network.availableAgentsInTS;
		//For performance 
		if(this.targetValue == 1 && this.carlos.network.availableNegAgentsInTS.size() >0){
			list=this.carlos.network.availableNegAgentsInTS; // You dont want to work on those who like you anyways 
			//System.out.println("TakePromessing: Take Neg fast list");
		}
		else if(this.targetValue == -1 && this.carlos.network.availablePosAgentsInTS.size() >0){
			list=this.carlos.network.availablePosAgentsInTS; // You dont want to work on those who like you anyways 
		}
		
		
		for(Agent agent : list){
			//if(agent.getCurrentOpinion()==targetValue){	//Should be coverd by the list now
			//	continue; //makes it damn slow!
			//}
			double tauPos = carlos.network.getTauPos(agent, ts-1);
			if(targetValue == 1 && tauPos>carlos.tau){
				//System.out.println("TakePromissing: found +1 agent");
				return agent;
			}
			if(targetValue == -1 && (1-tauPos)>carlos.tau){
				//System.out.println("TakePromissing: found -1 agent");
				return agent;
			}
			
		}
		
		return this.carlos.network.availableAgentsInTS.getFirst();
	}

}
