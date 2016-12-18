package network;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.google.common.base.Function;

import edu.uci.ics.jung.algorithms.metrics.Metrics;
import edu.uci.ics.jung.algorithms.shortestpath.DistanceStatistics;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;


public class Network {
	public HashMap<Integer, Agent> agentMap = new HashMap<Integer, Agent>();
	public UndirectedSparseGraph<Agent, MEdge> graph = new UndirectedSparseGraph<Agent, MEdge>();
	public LinkedList<Agent> availableAgentsInTS = new LinkedList<Agent>(); //holds agents which were not hit yet
	public LinkedList<Agent> availablePosAgentsInTS = new LinkedList<Agent>(); //holds agents which were not hit yet
	public LinkedList<Agent> availableNegAgentsInTS = new LinkedList<Agent>(); //holds agents which were not hit yet
	int tauPosTS=-1; //Network calculates all tau pos for this ts. If a ts != this is requested they have to be recalculated 
	
	public Network() {
		// TODO Auto-generated constructor stub
	}
	
	public void addAgent(Agent agent){
		agentMap.put(agent.id, agent);
		graph.addVertex(agent);
	}
	
	public int addEdge(Agent a, Agent b){
		if(graph.isNeighbor(a, b)){
			return -1;
		}
		if(a == b){
			return -1;
		}
		graph.addEdge(new MEdge(), a, b);
		return 1;
	}
	
	public int addEdgeByIDs(int a, int b){
		if(!this.agentMap.containsKey(a) || !this.agentMap.containsKey(b)){
			return -1;
		}
		Agent aA = this.agentMap.get(a);
		Agent aB = this.agentMap.get(b);		
		return this.addEdge(aA, aB);
	}
	
	public Collection<Agent> getAllAgents(){
		return agentMap.values();
	}
	
	public Agent getAgent(int id){
		return agentMap.get(id);
	}
	
	public int checkConsitency(){
		int lengthOpList = 0;
		for(Agent agent : getAllAgents()){
			//Check length of opinion list
			if(lengthOpList == 0){
				lengthOpList=agent.getSizeOpinionList();
			}else{
				if(lengthOpList != agent.getSizeOpinionList()){
					System.out.println("Network: WARNING: Size of opinion records differ!");
					return-1;
				}
			}
			
			//check opinion 
			if(agent.getCurrentOpinion() < -1 || agent.getCurrentOpinion() > 1){
				System.out.println("Network: WARNING: Opinion out of range -1 to 1");
				return-1;
			}
			
		}
		
		//System.out.println("Network:consistency check ok");
		return 0;
	}
	
	public double getTauPos(Agent agent, int ts){
		if(this.tauPosTS != ts){
			//System.out.println("Network: Calculate t pos for ts" + ts);
			for(Agent currentAgent : this.getAllAgents()){
				currentAgent.tauPos=this.getTauPosCalc(currentAgent, ts);
			}
			this.tauPosTS=ts;
		}
		return agent.tauPos;
	}
	
	public double getTauPosCalc(Agent agent, int ts){
		//Returns the share of neighbours having opinion larger than zero in ts 
		int neighbours = 0;
		int posNeighbours = 0;
		for(Agent neighbour : graph.getNeighbors(agent)){
			neighbours ++;
			if(neighbour.getOpinionAt(ts) > 0){
				posNeighbours++;
			}
		}
		if(neighbours == 0){
			System.out.println("Network: WARNING: Calculated tau for agent without neighbours");
			return 0;
		}
		//System.out.println(posNeighbours+" "+neighbours);
		return 1.0*posNeighbours/neighbours;
	}
	
	public void deleteZeroNeighbourAgents(){
		int count = 0;
		LinkedList<Agent> removeAgents = new LinkedList<Agent>();
		for(Agent agent : getAllAgents()){
			if(graph.getNeighborCount(agent) == 0){
				removeAgents.add(agent);
				count++;
			}
		}
		
		deleteAgents(removeAgents);
		
		System.out.println("Network: Number of removed agents: "+count);
	}
	
	public void deleteAgents(LinkedList<Agent> agentDelList){
		for(Agent agent : agentDelList){
			graph.removeVertex(agent);
			agentMap.remove(agent.id);
		}
	}
	
	public void printAllCurrentOpinions(){
		System.out.println("opinions");
		for(Agent agent : getAllAgents()){
			System.out.print(agent.getCurrentOpinion()+1+"\t");
		}
		System.out.println("---");
		return;
	}
	
	public void analyse(){
		System.out.println("Network: Calculate diameter");
		Double diameter = DistanceStatistics.diameter(graph);
		System.out.println("Network: Calculate Clustering Coefficients");
		Map<Agent, Double> clusteringCoeff = Metrics.clusteringCoefficients(graph);
		System.out.println("Netowrk: Diameter: " + diameter);
		
		System.out.println("Network: Calculate average distances");
		Function<Agent, Double> avgDist = DistanceStatistics.averageDistances(graph);
		
		double overAllAvgDist = 0;
		double totalAvgDists = 0;
		double avgClusterCoeff = 0;
		double totalOfClusterCoeff=0;
		double degreeSum = 0;
		double averageDegree = 0;
		for(Agent agent : this.getAllAgents()){
			//System.out.println("Network: Agents avg Dist: "+avgDist.apply(agent));
			totalAvgDists=totalAvgDists+avgDist.apply(agent);
			//System.out.println("Network: Agents clust coeff: "+avgDist.apply(agent));
			totalOfClusterCoeff=totalOfClusterCoeff+clusteringCoeff.get(agent);
			degreeSum=degreeSum+graph.degree(agent);
		}
		overAllAvgDist=totalAvgDists/graph.getVertexCount();
		avgClusterCoeff=totalOfClusterCoeff/graph.getVertexCount();
		averageDegree=degreeSum/graph.getVertexCount();
		System.out.println("Network: Average of average distances "+overAllAvgDist);
		System.out.println("Network: Average of local clustering coefficients "+avgClusterCoeff);
		System.out.println("Network: Average Degree "+averageDegree);
		
	}
	
	public void generateAvailableAgents(int ts){ // collect all the agents which got not yet hit
		availableAgentsInTS = new LinkedList<Agent>();
		availablePosAgentsInTS = new LinkedList<Agent>();
		availableNegAgentsInTS = new LinkedList<Agent>();
		
		for(Agent agent : getAllAgents()){
			if(agent.getOpinionRecord().size()-1 < ts){
				availableAgentsInTS.add(agent);
				if(agent.getCurrentOpinion()==1){
					availablePosAgentsInTS.add(agent);
					//System.out.println("Network: Added agent to pos fast list");
				}else{
					availableNegAgentsInTS.add(agent);
					//System.out.println("Network: Added agent to neg fast list");
				}
				
			}
		}
	}

}
