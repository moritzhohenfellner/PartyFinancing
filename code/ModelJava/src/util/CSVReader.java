package util;



import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

public class CSVReader {
	boolean jsonl = false;
	public  LinkedList<String[]> readCSV(String fileName){
		return readCSV(fileName, "\t");
	}
	
	public  LinkedList<String[]> readJSONL(String fileName){
		jsonl=true;
		return readCSV(fileName, ",");
	}
	
	
	public  LinkedList<String[]> readCSV(String fileName, String trennzeichen){
		BufferedReader reader = null;
		LinkedList<String[]> liste = new LinkedList<String[]>();
		try {
			reader = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String zeile = "";
		
		try {
			zeile = reader.readLine();
			while ((zeile = reader.readLine()) != null) {
				if(jsonl){
					zeile = zeile.replace("[", "").replace("]", "");
				}
				String[] felder = zeile.split(trennzeichen,0);
				if(felder.length>1 || true){	// Wenn nur eine spalte 0 setzen //jsonl file has empty agents >> true
					liste.add(felder);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //header
		try {
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return liste;
	}
}
