package network;

import java.util.LinkedList;

public class Agent {
public int id;
public int cluster = -1;
public double tauPos = -1.0;
private LinkedList<Double> opinionRecord = new LinkedList<Double>(); 
//Using an array would probably result in better performance but 
//the linked list is comfortable for coding since we do not
//Need to define the length at the beginning and we dont need to
//Pass the time step to the agent 
//BUT we have to make sure that each agent gets his opinin changed
//exactly once per time step


	public Agent(int id) {
		// TODO Auto-generated constructor stub
		this.id = id;
	}
	
	public double getCurrentOpinion(){ //If used during opinion update this can be one TS a head
		return opinionRecord.getLast();
	}

	public void setOpinion(double d) {
		// TODO Auto-generated method stub
		opinionRecord.add(d);
	}
	
	public double getOpinionAt(int ts){
		return opinionRecord.get(ts);
	}
	
	public int getSizeOpinionList(){
		return opinionRecord.size();
	}
	
	public void clearRecord(){
		opinionRecord = new LinkedList<Double>(); 
	}
	
	public LinkedList<Double> getOpinionRecord(){
		return this.opinionRecord;
	}

}
