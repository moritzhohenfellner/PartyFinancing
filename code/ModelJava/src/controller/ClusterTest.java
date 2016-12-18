package controller;

import network.ClusterFinder;
import network.Network;
import util.JasonReader;

public class ClusterTest {

	public ClusterTest() {
		
	}
	
	public static void main(String[] args){
		// TODO Auto-generated constructor stub
		String inputFile = "input/coauthornetwork_XA_2008.jsonl";
		inputFile = "input/coauthornetwork_BU_2008.jsonl";
		JasonReader jsr = new JasonReader(inputFile);
		Network network = jsr.read();
		
		ClusterFinder cf = new ClusterFinder();
		cf.reduceToBiggestComponent(network);
		cf.identifyClusters(network);
		
		
	}

}
