package fileParse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.io.FileReader;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

public class JsonFileReader {
	public static void main(String[] args) throws Exception{
		ArrayList<String> paramNameList = new ArrayList<>();
		ArrayList<String> equivClassName = new ArrayList<>();
		ArrayList<Parameter> pList = new ArrayList<>();

		
		//Object obj = new JSONParser().parse(new FileReader("ExecutionQueueOnSave.json"));
        //Object obj = new JSONParser().parse(new FileReader("QuadraticEquationSolver.json"));
        Object obj = new JSONParser().parse(new FileReader("OrgLevelUnits.json"));

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
        	System.out.println("Parameter Name: " + temp.getName() + " | Equivalence classes: " + temp.getEquivalenceClasses());
        	//System.out.println(temp.getEquivalenceClasses());
        	paramList.add(temp.getEquivalenceClasses());
        }
        System.out.println("");
        
        
        /*
        for(int i = 0; i < paramList.get(0).size(); i++) {
        	for (int j = 0; j < paramList.get(1).size(); j++) {
        		for (int k = 0; k < paramList.get(2).size(); k++) {
        			tempList.add(new String[] {paramList.get(0).get(i), paramList.get(1).get(j), paramList.get(2).get(k)});
        		}
        	}
        }
        */
        
        
        
        for (String[] temp : tempList) {
        	System.out.println(Arrays.toString(temp));
        	//System.out.println("Given Parameters: " + paramNameList + ", When they are" + Arrays.toString(temp));
        }
        
	}
}
