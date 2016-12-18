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

public class MultipleMain {

	public MultipleMain() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("lets go");
		
		//input file
		String gridFile = "input/grid.csv";
		gridFile = "input/peqsgrid20.csv";
		gridFile = "input/detail.csv";

		//time steps
		int maxTS = 200; // ok for small network 

		ParamGridReader paramGridReader = new ParamGridReader(gridFile);
		
		
		String inputFileSmall = "input/coauthornetwork_XA_2008.jsonl";
		String inputFileBig="input/coauthornetwork_BU_2008.jsonl";
		
		
		//Init RNG
		GlobalRandom grand = new GlobalRandom();
		grand.start((long) 1);
		
		LinkedList<ParamSet> paramGrid = paramGridReader.sets;
		
		maxTS=300; //jar 3
		maxTS=150; //jar 4
		
		go(paramGrid, prepNetwork(inputFileSmall), maxTS, new TakeRandom(), new TakeRandom(), "D150Out/smallStupidStuipd/");
		go(paramGrid, prepNetwork(inputFileSmall), maxTS, new TakeProtect(), new TakeRandom(), "D150Out/smallSwingStupid/");
		go(paramGrid, prepNetwork(inputFileSmall), maxTS, new TakeConvincible(), new TakeRandom(), "D150Out/smallPromStupid/");
		go(paramGrid, prepNetwork(inputFileSmall), maxTS, new TakeProtect(), new TakeConvincible(), "D150Out/smallSwingProm/");
		go(paramGrid, prepNetwork(inputFileSmall), maxTS, new TakeProtect(), new TakeProtect(), "D150Out/smallSwingSwing/");
		go(paramGrid, prepNetwork(inputFileSmall), maxTS, new TakeConvincible(), new TakeConvincible(), "D150Out/smallPromProm/");
		go(paramGrid, prepNetwork(inputFileSmall), maxTS, null, null, "D150Out/smallNullNull/");
		
		/*
		go(paramGrid, prepNetwork(inputFileBig), maxTS, new StupidTakeNext(), new StupidTakeNext(), "out/bigStupidStuipd/");
		go(paramGrid, prepNetwork(inputFileBig), maxTS, new TakeSwing(), new StupidTakeNext(), "out/bigSwingStupid/");
		go(paramGrid, prepNetwork(inputFileBig), maxTS, null, null, "out/bigNullNull/");
		go(paramGrid, prepNetwork(inputFileBig), maxTS, new TakeSwing(), new TakeSwing(), "out/bigSwingSwing/");
		go(paramGrid, prepNetwork(inputFileBig), maxTS, new TakePromissing(), new TakePromissing(), "out/bigPromProm/");
		go(paramGrid, prepNetwork(inputFileBig), maxTS, new TakePromissing(), new StupidTakeNext(), "out/bigPromStupid/");
		go(paramGrid, prepNetwork(inputFileBig), maxTS, new TakeSwing(), new TakePromissing(), "out/bigSwingProm/");
		*/
		
		System.out.println("all done");
	}
	
	static Network prepNetwork(String inputFile){
		Network network;
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
		return network;

	}
	
	static void go(LinkedList<ParamSet> paramGrid, Network network, int maxTS, Strategy stratA, Strategy stratB, String outPath){
		File blankDir = new File(outPath);
		blankDir.mkdir();
		
		outPath = outPath + Long.toString(System.currentTimeMillis());
		
		blankDir = new File(outPath);
		blankDir.mkdir();
		
		CSVWriter writer = new CSVWriter(outPath+"/mruns");
		
		
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
			//run.writeSignalRecord(outPath, i);
			i++;
		}
		
		writer.close();
		
	}

}
