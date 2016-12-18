package util;

import java.util.LinkedList;

public class ParamGridReader {
	public LinkedList<ParamSet> sets = new LinkedList<ParamSet>();
	public ParamGridReader(String gridFileName) {
		// TODO Auto-generated constructor stub
		CSVReader csv = new CSVReader();
		LinkedList<String[]> importList = csv.readCSV(gridFileName, ",");
		
		boolean head = true;
		for(String[] line : importList){
			if(head){
				head=false;
				continue;
			}
			
			ParamSet set = new ParamSet();
			set.initialPosShare = Double.parseDouble(line[1]); //index 0 is run number
			set.tau = Double.parseDouble(line[2]);
			set.posMassMediaShare = Double.parseDouble(line[3]);
			sets.add(set);
		}
		
		
	}

}
