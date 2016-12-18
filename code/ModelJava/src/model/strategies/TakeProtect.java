package model.strategies;

import java.util.LinkedList;

import network.Agent;
import model.Strategy;

public class TakeProtect extends Strategy {

	public TakeProtect() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Agent selectNextAgent(int ts){
		//first safe yours which could go to the other side
		//then steel from the others

		LinkedList<Agent> list;
		LinkedList<Agent> yours = null;
		LinkedList<Agent> others = null;
		list=this.carlos.network.availableAgentsInTS;

		if(this.targetValue == 1){
			yours = this.carlos.network.availablePosAgentsInTS;
			others = this.carlos.network.availableNegAgentsInTS; 
		}
		else if(this.targetValue == -1){
			others = this.carlos.network.availablePosAgentsInTS;
			yours = this.carlos.network.availableNegAgentsInTS; 
		}
		
		//safe yours
		for(Agent agent : yours){
			double tauPos = carlos.network.getTauPos(agent, ts-1);
			if(targetValue == 1 && (1-tauPos) < carlos.tau){
				continue; //safe
			}
			
			if(targetValue == -1 && (tauPos) < carlos.tau){
				continue; //safe
			}
			
			//System.out.println("TakeSing: found one");
			return agent;
		}
		
		//Steel others which you can get
		for(Agent agent: others){
			double tauPos = carlos.network.getTauPos(agent, ts-1);
			if(targetValue == 1 && (tauPos) < carlos.tau){
				continue; //lost anyway
			}
			
			if(targetValue == -1 && (1-tauPos) < carlos.tau){
				continue; //lost anyway
			}
			//System.out.println("TakeSing: found one");
			return agent;
		}
		
		return this.carlos.network.availableAgentsInTS.getFirst();
	}

}
