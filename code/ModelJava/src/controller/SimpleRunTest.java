package controller;

import java.io.File;
import java.util.LinkedList;

import model.MassMediaCarlos;
import model.Strategy;
import model.strategies.TakeRandom;
import model.strategies.TakeConvincible;
import model.strategies.TakeProtect;
import network.Agent;
import network.ClusterFinder;
import network.Network;
import util.CSVWriter;
import util.GlobalRandom;
import util.JasonReader;
import util.ParamGridReader;
import util.ParamSet;

public class SimpleRunTest {

	public SimpleRunTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("lets go");
		
		//input file
		String gridFile = "input/grid.csv";
		gridFile = "input/peqsgrid20.csv";
		gridFile = "input/tfixed1.csv";
		
		ParamGridReader paramGridReader = new ParamGridReader(gridFile);
		String inputFile = "input/coauthornetwork_XA_2008.jsonl";
		//inputFile="input/coauthornetwork_BU_2008.jsonl";
		
		//time steps
		//int maxTS = 150;
		int maxTS = 300; // ok for small network 
		maxTS=102;
		//Define Strategies
		Strategy stratA=null; //Leave null >> No strategy will be used
		Strategy stratB=null; //Leave null >> No strategy will be used
		
		//stratA = new StupidTakeNext();
		//stratB = new StupidTakeNext(); //change the order in stupid otherwise allways the same order - done
		
		
		
		//Init RNG
		GlobalRandom grand = new GlobalRandom();
		grand.start((long) 1);
		
		LinkedList<ParamSet> paramGrid = paramGridReader.sets;
		Network network;
		
		//Prepare output 
		boolean staticDirTest = false;
		String outPath = "output/"+Long.toString(System.currentTimeMillis());
		if(staticDirTest){
			outPath = "output/testRun";
		}
		File blankDir = new File(outPath);
		blankDir.mkdir();
		CSVWriter writer = new CSVWriter(outPath+"/mruns");
		
		
		//prepare network
		System.out.println("SimpleRunTest: read network from file");
		JasonReader jsr = new JasonReader(inputFile);
		network = jsr.read();
		
		//Delate agents without neigbours 
		network.deleteZeroNeighbourAgents();
		
		//Only use biggest component
		ClusterFinder cf = new ClusterFinder();
		cf.reduceToBiggestComponent(network);
		cf.identifyClusters(network);
		//Analyse the graph
		//network.analyse();
		
		
		System.out.println("Number of runs: "+paramGrid.size());
		
		
		SimpleRun run;
		int i=0;
		for(ParamSet set : paramGrid){
			//run = new SimpleRun(100000, 4, 0.3, 0.5, 0.2, 0.6, 1, "input/coauthornetwork_BU_2008.jsonl");
			//run = new SimpleRun(0.5, 0.2, 0.6, 1, "input/coauthornetwork_XA_2008.jsonl");
			if(i%10==0){
				System.out.println("Start run "+i);
			}
			
			
			run = new SimpleRun(set.initialPosShare, set.tau, set.posMassMediaShare, maxTS, network, stratA, stratB);
			run.record.addConstItem("runNumber", Integer.toString(i));
			run.record.addConstItem("p", Double.toString(set.initialPosShare));
			run.record.addConstItem("theta", Double.toString(set.tau));
			run.record.addConstItem("pMedia", Double.toString(set.posMassMediaShare));
			
			if(i==0){
				writer.writeAll(run.record.convertToLines(true, true));
			}else{
				writer.writeAll(run.record.convertToLines(true, false));
			}
			writer.flush();
			run.writeSignalRecord(outPath, i);
			
			i++;
		}
		
		writer.close();
		
		
		System.out.println("done");
	}

}
