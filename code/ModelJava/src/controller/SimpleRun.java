package controller;

import javax.json.JsonReader;

import model.InitializeAgentsOpinions;
import model.MassMediaCarlos;
import model.Strategy;
import network.ClusterFinder;
import network.Network;
import network.NetworkEvaluator;
import network.WattsStrogatz;
import util.CSVWriter;
import util.GlobalRandom;
import util.JasonReader;
import util.Record;

public class SimpleRun {
	Record record = new Record();
	Network network = null;
	NetworkEvaluator evaluator = new NetworkEvaluator();
	WattsStrogatz watts = new WattsStrogatz();
	InitializeAgentsOpinions  init = new InitializeAgentsOpinions();
	MassMediaCarlos carlos;
	
	int ts=0;

	public SimpleRun(double initPosShare, double tau, double posMassMediaShare, int maxTs, Network network, Strategy stratA, Strategy stratB){
		this(0, 0, 0.0, initPosShare, tau, posMassMediaShare, maxTs, network, stratA, stratB);
	}
	
	public SimpleRun(double initPosShare, double tau, double posMassMediaShare, int maxTs, Network network){
		this(0, 0, 0.0, initPosShare, tau, posMassMediaShare, maxTs, network, null, null);
	}
	
	public SimpleRun(int numberOfAgents, int avgDegree, double beta, double initPosShare, double tau, double posMassMediaShare, int maxTs, Network inputNetwork, Strategy stratA, Strategy stratB) {
		// TODO Auto-generated constructor stub
		//Prepare record
		//System.out.println("SimpleRun: Start run");
		record.addRecordItem("posOpinionShare");
		
		
		//Prepare network
			if(inputNetwork == null){
				inputNetwork = watts.createSmallWorld(numberOfAgents, avgDegree, beta); //if no network and no input file is given
			}else{
				this.network=inputNetwork;
			}
			
		//Assign initial opinions	
		init.init(network, initPosShare);
		
		//Add inital state
		record.addTimeStep("posOpinionShare", evaluator.posOpShare(network, 0));

		
		//Prepare model
		carlos = new MassMediaCarlos(network, tau, posMassMediaShare);
		
		if(!(stratA == null || stratB == null)){
			//System.out.println("SimpleRun: Prepare Strategies");
			stratA.targetValue=1;
			stratB.targetValue=-1;
			stratA.carlos = carlos;
			stratB.carlos = carlos;
		}
		
		//Iterate
		ts=1;
		while(ts < maxTs){
			if(stratA == null || stratB == null){
				carlos.performTimeStep(ts);
			}else{
				performStartegyTSParallel(stratA, stratB, posMassMediaShare);
			}
			if(ts%50 == 0 || ts == maxTs-1){
				record.addTimeStep("posOpinionShare", evaluator.posOpShare(network, ts));
			}
			ts++;				
		}
		network.checkConsitency();
		//System.out.println("SimpleRun: finished run");
	}
	

	
	private void performStartegyTSParallel(Strategy stratA, Strategy stratB, double posMassMediaShare){
		//Strategies take turns alternating 
		network.generateAvailableAgents(ts);
		int totalNumber = network.availableAgentsInTS.size();
		int maxA = (int) Math.round(totalNumber*posMassMediaShare); //how many is A allowed to hit 
		int maxB = totalNumber - maxA;
		

		int hitA = 0;
		int hitB = 0;
			while(network.availableAgentsInTS.size() >0){
				if(true){//Take turns
				if(hitA<maxA){
					stratA.hitNextAgent(ts);
					hitA++;
				}
				if(hitB<maxB){
					stratB.hitNextAgent(ts);
					hitB++;
				}
				}else{// Draw randomly
					if(GlobalRandom.rand.nextDouble() < posMassMediaShare){
						stratA.hitNextAgent(ts);
					}else{
						stratB.hitNextAgent(ts);
					}
				}
			}
			network.checkConsitency();
		carlos.finishTS(ts);//in case of uneven agent number 
		//System.out.println("SimpleRun: finished ts");
	}

	public void writeSignalRecord(String outPath, int runNumber){
		CSVWriter writer = new CSVWriter(outPath+"/zmruns"+runNumber);
		writer.writeAll(carlos.signalRecord.convertToLines(false, true));
		writer.close();
	}
	
}
