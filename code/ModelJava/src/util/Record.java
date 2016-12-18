package util;

import java.util.HashMap;
import java.util.LinkedList;


public class Record {
	HashMap<String, LinkedList<String>> map = new HashMap<String, LinkedList<String>>();
	//<Name of Variable, <Values over time>

	public Record() {
		// TODO Auto-generated constructor stub
	}
	
	public void addConstItem(String name, String value){
		LinkedList<String> constV = new LinkedList<String>();
		for(int ts = 0; ts < map.values().iterator().next().size(); ts++){
			constV.add(value);
		}
		this.addRecordItem(name, constV);
	}
	
	public void addRecordItem(String name){
		this.addRecordItem(name, new LinkedList<String>());
	}

	public void addRecordItem(String name, LinkedList<String> values){
		map.put(name, values);
	}

	public void addTimeStep(String itemName, double value){
		this.map.get(itemName).add(Double.toString(value));
	}

	public void addRecordItemDouble(String name, LinkedList<Double> values){
		LinkedList<String> stringList = new LinkedList<String>();
		for(double value : values){
			stringList.add(Double.toString(value));
		}
		this.addRecordItem(name, stringList);
	}

	public LinkedList<LinkedList<String>> convertToLines(boolean generateTimeSteps, boolean writeHeads){
		LinkedList<LinkedList<String>> lines = new LinkedList<LinkedList<String>>();
		LinkedList<String> heads = new LinkedList<String>();

		//Create ts column 
		if(generateTimeSteps){
			LinkedList<Double> timeSteps = new LinkedList<Double>();
			for(int ts = 0; ts < map.values().iterator().next().size(); ts++){
				timeSteps.add(ts*1.0);
			}
			this.addRecordItemDouble("ts", timeSteps);
		}

		//Get heads
		for(String head : map.keySet()){
			heads.add(head);
		}
		if(writeHeads){
			lines.add(heads);
		}
		
		//Get lines
		for(int i = 0; i < map.get(heads.getFirst()).size(); i++){
			LinkedList<String> line = new LinkedList<String>();
			for(String head : heads){
				line.add(map.get(head).get(i));
			}
			lines.add(line);
		}

		return lines;
	}

}
