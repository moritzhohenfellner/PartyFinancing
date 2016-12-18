package controller;

import network.ClusterFinder;
import network.Network;
import util.JasonReader;

public class AnalyseNetwork {

	public AnalyseNetwork() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String inputFile = "input/coauthornetwork_XA_2008.jsonl";
		//inputFile="input/coauthornetwork_BU_2008.jsonl";


		//prepare network
		System.out.println("read network from file");
		JasonReader jsr = new JasonReader(inputFile);
		Network network = jsr.read();

		//Delate agents without neigbours 
		network.deleteZeroNeighbourAgents();

		//Only use biggest component
		ClusterFinder cf = new ClusterFinder();
		cf.reduceToBiggestComponent(network);
		cf.identifyClusters(network);
		//Analyse the graph
		network.analyse();



	}

}
