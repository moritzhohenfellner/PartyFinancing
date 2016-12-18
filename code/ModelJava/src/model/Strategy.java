package model;

import network.Agent;
import network.Network;

public class Strategy {
	public double targetValue=0; //Opinion value the stratgy should apply  
	public MassMediaCarlos carlos;
	public Strategy() {
		// TODO Auto-generated constructor stub

	}
	
	public Agent selectNextAgent(int ts){
		return null;
	}
	
	public void performTS(int contingent, int ts){
		int i=0;
		while(i<contingent){
			Agent agent = selectNextAgent(ts);
			if(hitAgent(agent, ts) != -200){//-200 >> agent was already hit, take an other one
				//but the selecting algorithm should only select available agents
				i++;
			}
		}
	}
	
	public int hitNextAgent(int ts){
		if(hitAgent(selectNextAgent(ts), ts) == -200){//try to hit the next selcted agent until you find one
			System.out.println("Strategy: Tried to hit unavailable agent");
		}
		return 0;//0 >> io; -1 >> no more agents 
	}
	
	public int hitAgent(Agent agent, int ts){
		return carlos.hitAgent(agent, targetValue, ts);
	}
	

}
