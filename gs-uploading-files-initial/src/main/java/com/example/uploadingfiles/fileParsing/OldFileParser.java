package com.example.uploadingfiles.fileParsing;

import java.io.FileReader;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

@Service
public class OldFileParser {
	
	public OldFileParser() {
		
	}
	
	public static void main(String[] args) throws Exception {
		
		OldFileParser ofp = new OldFileParser();
		
		ArrayList<Parameter> arrList = ofp.parseFile("upload-dir/ExecutionQueueOnSave.json"); 
		//ArrayList<Parameter> arrList = ofp.parseFile("upload-dir/ExpressionsExampleWithRecursion.json");
		//ArrayList<Parameter> arrList = parseFile("IcmVerboseLogging_Expiration.json");
		//ArrayList<Parameter> arrList = parseFile("MultiTenantOnboardingSecurity.json");
		//ArrayList<Parameter> arrList = parseFile("QuadraticEquationSolver.json");
		//ArrayList<Parameter> arrList = ofp.parseFile("OrgLevelUnits.json");
		int count = 1;
		
		for (Parameter temp : arrList) {
			System.out.println("Parameter Name: " + temp.getName() + " | Equivalence Classes: " + temp.getEquivalenceClasses());
			count = count * temp.getEquivalenceClasses().size();
		}
		
		String[][] com = ofp.createCombos(arrList, count);
		
		//for (String[][] temp : com) {
		////	System.out.println(Arrays.toString(temp));
		//}
	
	}
	
	public String[][] createCombos(ArrayList<Parameter> arrList, int count) {
        ArrayList<ArrayList<String>> paramList = new ArrayList<>(); // stores the strings of each parameters equiv classes
		ArrayList<String[][]> tempList = new ArrayList<>(); //stores the combos in an array
		
		//populate the paramList ArrayList with the equivalence Classes
	    for (Parameter temp : arrList) {
	    	paramList.add(temp.getEquivalenceClasses());
	    }
		
		int totalParams = arrList.size(); // amount of parameters
		int currentParam = paramList.get(totalParams-1).size(); // the equivclass
		int change = 1; // use this to show when to change the equivClass
		int checkChange = change * currentParam; // use this to exit the change loop and reset
		String[][] paramArray = new String[count][totalParams]; // array with the size of the total amount of params
		int temp = count-1; //temp variable used to represent the total number of combinations - 1 (rows)
		int check = totalParams; // use this as a decision maker in the if statement
		
		if (count == 1) {
			
		}
		else {
			while (totalParams > 0) { // keep going until every parameter is ran through
				while (temp > -1) { //keep going until every row is populated 
					//for (int i = 0; i < currentParam; i++) { // use i to 
						for (int j = 0; j < currentParam; j++) { // use j to populate the matrix 
							if ((check != totalParams) && (totalParams > 0)) { // this is used to decide which logic to use
								change = 0; // reset change so it can iterate again
								while (change < checkChange) { 
									paramArray[temp][totalParams-1] = paramList.get(totalParams-1).get(j);
									tempList.add(paramArray);
									temp--;
									change++;
								}
								//change = 0;
							} else {
								paramArray[temp][totalParams-1] = paramList.get(totalParams-1).get(j);
								tempList.add(paramArray);
								temp--;
								//change++;
							}
						}
					//}
					
				}
				temp = count-1; // reset temp
				totalParams--; //change the parameter
				checkChange = change * currentParam; // update checkChange
				if (totalParams > 0) { // use this to update currentParam while totalParams > 1
					currentParam = paramList.get(totalParams-1).size();
				}
			}
		}
		//int c = 0;
		//use this to print out each element of the matrix
		for (int row = 0; row < paramArray.length; row++) {
			for (int column = 0; column < paramArray[row].length; column++) {
				System.out.print(paramArray[row][column] + " ");
			}
			//c++;
			System.out.println();
			//System.out.println(c);
		}
		//return tempList;
		return paramArray;
	}

	public ArrayList<Parameter> parseFile(String fileName) throws Exception {
		ArrayList<String> paramNameList = new ArrayList<>();
		//ArrayList<String> equivClassName = new ArrayList<>();
		ArrayList<Parameter> pList = new ArrayList<>();

	    Object obj = new JSONParser().parse(new FileReader(fileName));
	
		JSONObject jo = (JSONObject) obj;
		
		Map inputParameters = ((Map)jo.get("InputParameters"));
	    
	    // iterating address Map
	    Iterator<Map.Entry> itr1 = inputParameters.entrySet().iterator();
	    while (itr1.hasNext()) {
	        Map.Entry pair = itr1.next();
	        //System.out.println(pair.getKey() + " : " + pair.getValue());
	        String paramNames = (String) pair.getKey();
	        paramNameList.add(paramNames); 
	        
			Parameter param = new Parameter();
			param.setName(paramNames);
			
	        JSONObject value = (JSONObject) pair.getValue();
	        //System.out.println(value);
	        Map equivClass = ((Map) value.get("EquivalenceClasses"));
	        Iterator<Map.Entry> itr2 = equivClass.entrySet().iterator();
	        while (itr2.hasNext()) {
	            Map.Entry pair2 = itr2.next();
	            //System.out.println(pair2.getKey() + " : " + pair2.getValue());
	            //equivClassName.add((String) pair2.getKey());
	            param.addParam((String)pair2.getKey());   
	        }
	        pList.add(param);
	    }
	    ArrayList<ArrayList<String>> paramList = new ArrayList<>();
	    ArrayList<String[]> tempList = new ArrayList<>();
	    
	    for (Parameter temp : pList) {
	    	//System.out.println("Parameter Name: " + temp.getName() + " | Equivalence classes: " + temp.getEquivalenceClasses());
	    	//System.out.println(temp.getEquivalenceClasses());
	    	paramList.add(temp.getEquivalenceClasses());
	    }
	    
	    return pList;
	}
}
