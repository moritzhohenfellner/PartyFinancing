package network;

import java.util.Random;

import javax.swing.text.StyleContext.SmallAttributeSet;

import util.GlobalRandom;

public class WattsStrogatz {
	int n;
	int k;
	double beta;
	Network swNetwork = new Network();
	
	public WattsStrogatz() {
		// TODO Auto-generated constructor stub
	}
	
	public Network createSmallWorld(int n, int k, double beta){//Number of nodes, average degree (should be even?), spatial param.
		this.n=n;
		this.k=k;
		this.beta=beta;
		
		if(k % 2 == 1){
			System.out.println("WattsTrogatz: k is univen that might cause a problem");
		}
		
		
		
		//Create n agents
		for(int i=0; i<n; i++){	
			swNetwork.addAgent(new Agent(i));
		}
		
		//Create circular edges
		System.out.println("WatzStrogatz: start building circular edges");
		for(int i=0; i<n; i++){//Not efficient not all of them needed anyways doubles and loops are refused by network class
			for(int dist = -k/2; dist<= k; dist++){
				int other = circleId(i+dist);
				swNetwork.addEdgeByIDs(i, other);

			}
			//System.out.println("Edges for verted "+i+" done");
		}
		
		
		//Rewire
		System.out.println("WattsStrogatz: start rewireing");
		Random bethaRand = GlobalRandom.rand;
		for(int i=0; i<n; i++){//Not efficient not all of them needed anyways doubles and loops are refused by network class
			Agent current = swNetwork.agentMap.get(i);
			//System.out.println("Rewire vertex "+i);
			for(MEdge ege : swNetwork.graph.getOutEdges(current)){
				Agent neighBour = swNetwork.graph.getOpposite(current, ege);
				if(neighBour.id<i){
					//System.out.println("backward link");
					continue;
				}
				//System.out.println("forward link");
				if(bethaRand.nextDouble()<this.beta){
					reWire(ege, current);
				}
			}
		}
				
		return swNetwork;
	}
	
	int circleId(int id){
		if(id >= 0 && id<n){
			return id;
		}
		if(id<0){
			return n+id;
		}
		if(id>n-1){
			return id-n;
		}
		return 0;
	}
	
	void reWire(MEdge oldE, Agent baseVertex){
		//Base vertex gets reconected to some other vertex
		Random rand = GlobalRandom.rand;
		
		//Remove old edge
		this.swNetwork.graph.removeEdge(oldE);
		
		//Choose uniform between vertexes until you find a valid one (No self loop no double)
		while(true){
			int newId = rand.nextInt(n);
			int success = swNetwork.addEdgeByIDs(newId, baseVertex.id);
			if(success == 1){
				return;
			}
		}
		
		
	}

}
