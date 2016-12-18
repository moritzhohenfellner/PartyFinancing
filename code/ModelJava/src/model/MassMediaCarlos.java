package model;

import java.util.Random;

import util.GlobalRandom;
import util.Record;
import network.Agent;
import network.Network;

public class MassMediaCarlos {
	double posMassMedShare;
	public double tau;
	public Network network;
	public Record signalRecord = new Record();

	public MassMediaCarlos(Network network, double tau, double posMassMedShare) {
		// TODO Auto-generated constructor stub
		this.network=network;
		this.tau=tau;
		this.posMassMedShare=posMassMedShare;

		signalRecord.addRecordItem("ts");
		signalRecord.addRecordItem("AgentID");
		signalRecord.addRecordItem("inputSignal");
		signalRecord.addRecordItem("previousOpinion");
		signalRecord.addRecordItem("tauPos");
	}

	public int performTimeStep(int ts){
		if(ts < 1){//ts 0 is the initial state. Calling the function for ts 0 would try to access ts -1
			System.out.println("MassMediaCarlos: WARNING: Called perform TimeStep for ts 0");
			return -1;
		}
		//System.out.println("MassMediaCarlos: perform ts "+ts);
		for(Agent agent : network.getAllAgents()){
			double opa = getOpinionToApply(); //opinion to apply 
			hitAgent(agent, opa, ts);
		}
		return 0;
	}

	public double getOpinionToApply(){
		Random rand = GlobalRandom.rand;
		if(rand.nextDouble() < posMassMedShare){
			return 1;
		}else{
			return -1;
		}
	}

	public int hitAgent(Agent agent, double opa, int ts){
		//if(agent.getOpinionRecord().size()-1 >= ts){	//Deactivated to save time 
		//	System.out.println("MassMediaCarlos: WARNING: Tried to hit agent twice");
		//	return -200;//Agent already has an opinion for this ts
		//}
		//System.out.println("MassMediaCarlos: hit agent "+agent.id);
		double opAgent = agent.getOpinionAt(ts-1);
		double tauPos = network.getTauPos(agent, ts-1);

		if(ts%50==0 || ts<5){
			signalRecord.addTimeStep("ts", ts);
			signalRecord.addTimeStep("AgentID", agent.id);
			signalRecord.addTimeStep("inputSignal", opa);
			signalRecord.addTimeStep("previousOpinion", opAgent);
			signalRecord.addTimeStep("tauPos", tauPos);
		}

		//Remove from lists (this might cost time)
		network.availableAgentsInTS.remove(agent); //Everybody only once per ts
		if(opAgent==1){
			network.availablePosAgentsInTS.remove(agent);
		}else{
			network.availableNegAgentsInTS.remove(agent);
		}

		//Check whether opinions coincide 
		if(opa == opAgent){//Agent already has this opinion
			agent.setOpinion(opAgent); //Keep your opinion 
			return 0;
		}

		//check for adoption
		if(opa == 1 && tauPos > tau){
			agent.setOpinion(opa);
			//System.out.println("MassMediaCarlos: op changed to pos");
			return 1;//changed opinion to 1
		}
		if(opa == -1 && (1-tauPos)>tau){
			agent.setOpinion(opa);
			//System.out.println("MassMediaCarlos: op changed to neg");
			return -1;//changed opinion to -1
		}

		agent.setOpinion(opAgent); //Keep your opinion 

		return 0;//no change
	}

	public void finishTS(int ts){
		//Set ts opinion for all agents who do not have one yet
		int shortCounter = 0;
		for(Agent agent : network.getAllAgents()){
			if(agent.getOpinionRecord().size()-1 < ts){//agent does not have an opinion for the ts
				agent.setOpinion(agent.getCurrentOpinion()); //keep opinion
				shortCounter++;
			}
		}
		//System.out.println("MassMediaCarlos: Number of untreated agents: "+shortCounter);
	}


}
