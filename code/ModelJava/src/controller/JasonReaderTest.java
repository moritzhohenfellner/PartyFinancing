package controller;

import java.util.LinkedList;

import network.Agent;
import network.Network;
import util.CSVReader;
import util.JasonReader;

public class JasonReaderTest {

	public JasonReaderTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//JasonReader jr = new JasonReader("input/coauthornetwork_BU_2008.jsonl");
		CSVReader reader = new CSVReader();
		LinkedList<String[]> input = reader.readJSONL("input/coauthornetwork_BU_2008.jsonl");
		Network jNetwork = new Network();

		int agentCount = input.size();
		
		System.out.println("Number of agents in file: "+agentCount);
		
		if(true)return;
		
		for(int i = 0; i<agentCount; i++){
			jNetwork.addAgent(new Agent(i));
		}
		
		int id = 0;
		for(String[] line : input){
			for(int j = 0; j<line.length; j++){
				int secondID = Integer.parseInt(line[j]);
				if(secondID > agentCount){
					System.out.println("Link to missing agent (id to high): "+secondID);
				}else{
					jNetwork.addEdgeByIDs(id, secondID);
				}
			}
			id++;
		}
		

	}

}
