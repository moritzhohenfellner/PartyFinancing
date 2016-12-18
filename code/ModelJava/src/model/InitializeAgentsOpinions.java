package model;

import java.util.Random;

import javax.swing.text.StyleContext.SmallAttributeSet;

import util.GlobalRandom;
import network.Agent;
import network.Network;

public class InitializeAgentsOpinions {

	public InitializeAgentsOpinions() {
		// TODO Auto-generated constructor stub
	}
	
	public void init(Network swNetwork, double sharePos){
		Random rand = GlobalRandom.rand;
		for(Agent agent : swNetwork.agentMap.values()){
			agent.clearRecord();
			if(rand.nextDouble() < sharePos){
				agent.setOpinion(1);
			}else{
				agent.setOpinion(-1);
			}
		}
	}

}
