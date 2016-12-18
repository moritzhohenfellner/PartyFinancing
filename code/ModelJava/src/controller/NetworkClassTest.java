package controller;

import java.util.Random;

import util.CSVWriter;
import util.Record;
import util.Viewer;
import model.InitializeAgentsOpinions;
import model.MassMediaCarlos;
import network.Agent;
import network.Network;
import network.NetworkEvaluator;
import network.WattsStrogatz;

public class NetworkClassTest {

	public NetworkClassTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		Network network = new Network();
		NetworkEvaluator evaluator = new NetworkEvaluator();
		CSVWriter writer = new CSVWriter("output/recordTest.csv");
		Record record = new Record();
		record.addRecordItem("posOpinionShare");
		
		WattsStrogatz watts = new WattsStrogatz();
		network = watts.createSmallWorld(200, 2, 0.5); //80
		
		InitializeAgentsOpinions  init = new InitializeAgentsOpinions();
		init.init(network, 0.3);
		
		network.checkConsitency();
		
		Viewer viewer = new Viewer();
		viewer.showGraph(network);
		network.printAllCurrentOpinions();
		
		MassMediaCarlos carlos = new MassMediaCarlos(network, 0.3, 0.6);
		record.addTimeStep("posOpinionShare", evaluator.posOpShare(network, 0));
		for(int ts = 1; ts<10; ts++){
			Thread.sleep(500);
			carlos.performTimeStep(ts);
			record.addTimeStep("posOpinionShare", evaluator.posOpShare(network, ts));
			viewer.reDraw();
		}

		network.printAllCurrentOpinions();
		
		record.addRecordItemDouble("Agent 1", network.getAgent(1).getOpinionRecord());
		
		writer.writeAll(record.convertToLines(true, true));
		writer.close();
	}

}
