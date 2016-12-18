package network;

import java.util.LinkedList;

public class ClusterFinder {
	Network network;
	int[] clusters;
	int maxId = 0;
	int maxSize = 0;
	
	public ClusterFinder() {
		// TODO Auto-generated constructor stub
	}
	
	public void identifyClusters(Network network){
		System.out.println("ClusterFinder: start identifying");
		//Use -Xss5M to aviod stack overflow >> huge recursion 
		this.network=network;
		clusters = new int[network.graph.getVertexCount()]; //Worth case ervry agent on its own 
		java.util.Arrays.fill(clusters, 0);
		int currentCluster = 0;
		
		for(Agent agent : network.getAllAgents()){
			agent.cluster=-1;
		}
		
		
		while(true){
			Agent start = null;
			//boolean everyNodeInACluster = true;
			for(Agent agent : network.getAllAgents()){
				if(agent.cluster == -1){//Agent is not yet in a cluster
					start = agent;
				}
			}
			if(start == null){
				break; //Every agent is in a cluster
			}
			mark(start, currentCluster);
			
			currentCluster++;
		}
		
		//Print and identify biggest component 
		for(int i = 0; i<clusters.length; i++){
			//System.out.println("Cluster "+i+" size: "+clusters[i]);
			if(clusters[i] > maxSize){
				maxSize=clusters[i];
				maxId=i;
			}
			if(clusters[i]==0){
				break;
			}
		}
		System.out.println("ClusterFinder: fineshed identifying");
	}
	
	void mark(Agent agent, int clusterID){
		agent.cluster=clusterID;
		clusters[clusterID]++;
		for(Agent neighbor : network.graph.getNeighbors(agent)){
			if(neighbor.cluster == -1){
				mark(neighbor, clusterID);
			}
		}
	}
	
	public void reduceToBiggestComponent(Network network){
		System.out.println("ClusterFinder: start reducing");
		this.identifyClusters(network);
		LinkedList<Agent> agentsToRemove = new LinkedList<Agent>();
		for(Agent agent : network.getAllAgents()){
			if(agent.cluster != this.maxId){
				agentsToRemove.add(agent);
			}
		}
		network.deleteAgents(agentsToRemove);
		System.out.println("ClusterFinder: fineshed reducing");
	}

}
