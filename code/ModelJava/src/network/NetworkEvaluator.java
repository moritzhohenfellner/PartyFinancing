package network;

import edu.uci.ics.jung.graph.Graph;

public class NetworkEvaluator {

	public NetworkEvaluator() {
		// TODO Auto-generated constructor stub
	}
	
	public double posOpShare(Network network, int ts){
		int pos = 0;
		
		for(Agent agent : network.getAllAgents()){
			if(agent.getOpinionAt(ts)>0){
				pos++;
			}
		}
		
		return 1.0*pos/network.graph.getVertexCount();
	}
	
	
	
	

}
