package util;

import java.util.LinkedList;

import network.Agent;
import network.Network;


public class JasonReader {

	String fileName;
	
	public JasonReader(String fileName) {
		this.fileName = fileName;
	}
	
	public Network read(){	
		CSVReader reader = new CSVReader();
		LinkedList<String[]> input = reader.readJSONL(fileName);
		Network jNetwork = new Network();

		int agentCount = input.size();
		
		System.out.println("Number of agents in file: "+agentCount);
		
		
		for(int i = 0; i<agentCount; i++){
			jNetwork.addAgent(new Agent(i));
		}
		
		int id = 0;
		for(String[] line : input){
			for(int j = 0; j<line.length; j++){
				if(line[j].equals("")){
					continue;
				}
				int secondID = Integer.parseInt(line[j]);
				if(secondID > agentCount){
					System.out.println("Link to missing agent (id to high): "+secondID);
				}else{
					jNetwork.addEdgeByIDs(id, secondID);
				}
			}
			id++;
		}

		System.out.println("done reading");
		return jNetwork;		
	}

}
