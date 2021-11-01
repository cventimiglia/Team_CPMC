package com.example.uploadingfiles.fileParsing;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

/**
 * Class: FileParser
 * @author Cameron Ventimiglia, Michelle Watson, Puen Xie, Constance Yang
 * Version: 1.0
 * Course: ITEC 3870
 * Written: September 16th, 2021
 * 
 * Purpose: The purpose of this class is to have the ability to parse a JSON file
 * in order to create Parameter objects. These Parameter objects will hold the name of the
 * input parameter in the file and an ArrayList<String> of the equivalence classes for that input parameter.
 * This class is also responsible for creating all possible combinations of equivalence classes. The goal is
 * to take this class and use it in the front-end in order to process files input by the user.
 */
@Service
public class FileParser {
	/**
	 * Constructor: FileParser()
	 * This constructor is used to create FileParser objects in order to call the methods from this class.
	 */
	public FileParser() {
		
	}
	
	/**
	 * method: createCombos
	 * creates a matrix that holds all of the combinations of equivalence classes. The matrix is 
	 * made by setting the rows to the amount of combinations and columns to the amount of parameters. The algorithm populates 
	 * the matrix from bottom to top. The parameter arrList represents an ArrayList that holds all the parameters. The parameter
	 * count represents the total amount of combinations.
	 * @param arrList
	 * @param count
	 * @return returns the populated matrix
	 */
	public String[][] createCombos(ArrayList<Parameter> arrList, int count) {
        ArrayList<ArrayList<String>> paramList = new ArrayList<>(); // stores the strings of each parameters equiv classes
		
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
		
		while (totalParams > 0) { // keep going until every parameter is ran through
			while (temp > -1) { //keep going until every row is populated 
					for (int j = 0; j < currentParam; j++) { // use j to populate the matrix 
						if ((check != totalParams) && (totalParams > 0)) { // this is used to decide which logic to use
							change = 0; // reset change so it can iterate again
							while (change < checkChange) { 
								paramArray[temp][totalParams-1] = paramList.get(totalParams-1).get(j);
								temp--;
								change++;
							}
						} else {
							paramArray[temp][totalParams-1] = paramList.get(totalParams-1).get(j);
							temp--;
						}
					}
			}
			temp = count-1; // reset temp
			totalParams--; //change the parameter
			checkChange = change * currentParam; // update checkChange
			if (totalParams > 0) { // use this to update currentParam while totalParams > 1
				currentParam = paramList.get(totalParams-1).size();
			}
		}
		
		return paramArray;
	}

	/**
	 * method: parseFile
	 * This method parses JSON files. It will add the input parameter name and equivalence classes and create Parameter objects
	 * using these. Once one Parameter object is made, it will add it to an ArrayList of type Parameter.
	 * @param fileName
	 * @return returns an ArrayList of Parameter objects
	 * @throws Exception
	 */
	public ArrayList<Parameter> parseFile(String fileName) throws Exception {
		ArrayList<String> paramNameList = new ArrayList<>();
		ArrayList<Parameter> pList = new ArrayList<>();

	    Object obj = new JSONParser().parse(new FileReader(fileName));
	
		JSONObject jo = (JSONObject) obj;
		
		Map inputParameters = ((Map)jo.get("InputParameters"));
	    
	    // iterating address Map
	    Iterator<Map.Entry> itr1 = inputParameters.entrySet().iterator();
	    while (itr1.hasNext()) {
	        Map.Entry pair = itr1.next();
	        String paramNames = (String) pair.getKey();
	        paramNameList.add(paramNames); 
	        
			Parameter param = new Parameter();
			param.setName(paramNames);
			
	        JSONObject value = (JSONObject) pair.getValue();
	        Map equivClass = ((Map) value.get("EquivalenceClasses"));
	        Iterator<Map.Entry> itr2 = equivClass.entrySet().iterator();
	        while (itr2.hasNext()) {
	            Map.Entry pair2 = itr2.next();
	            param.addParam((String)pair2.getKey());   
	        }
	        pList.add(param);
	    }
	    ArrayList<ArrayList<String>> paramList = new ArrayList<>();
	    ArrayList<String[]> tempList = new ArrayList<>();
	    
	    for (Parameter temp : pList) {
	    	paramList.add(temp.getEquivalenceClasses());
	    }
	    
	    return pList;
	}
}
