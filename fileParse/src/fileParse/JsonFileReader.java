package fileParse;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JsonFileReader {
	public static void main(String[] args) throws Exception{
		
		//JSONObject object = (JSONObject)new JSONParser().parse(new FileReader("ExecutionQueueOnSave.json"));
		//JSONObject object = (JSONObject)new JSONParser().parse(new FileReader("QuadraticEquationSolver.json"));
        JSONObject object = (JSONObject)new JSONParser().parse(new FileReader("OrgLevelUnits.json"));
		DataObject data = new DataObject();
		
		parseObject(data, object);
		//printObject(data);
		
		objectFieldTest(data);
		objectKeyTest(data);
		
		System.out.println();
        
	}
	
	public static void objectFieldTest(DataObject data)
	{
		System.out.printf("Name: %s\n", data.find("Name"));
		System.out.printf("Text: %s\n", data.find("Text"));
	}
	
	public static void objectKeyTest(DataObject data)
	{
		printObjectKeys((DataObject)data.find("ExpectedResults"));
		
		// for OrgLevelUnits
		printObjectKeys((DataObject)data.find("InputParameters"));
		System.out.println("==========================");
		
		printObjectKeys((DataObject)data.find("OrgLevel1"));
		System.out.println("==========================");
		
		DataObject obj = (DataObject)data.find("InputParameters");
		obj = (DataObject)obj.find("Ordering");
		printObjectKeys((DataObject)obj.find("EquivalenceClasses"));
	}
	
	public static void printObjectKeys(DataObject data)
	{
		for (Map.Entry<String, DataObject> entry : data.getChildObject().entrySet())
			System.out.println(entry.getKey());
	}
	
	// for verifying parseObject worked correctly
	public static void printObject(DataObject object)
	{
	    
		// print out key value fields
		for (Map.Entry<String, String> entry : object.getField().entrySet()) {
		    String key = entry.getKey();
		    String value = entry.getValue();
		    System.out.printf("%s: %s\n", key, value);
		}
		
		// print out json arrays
		for (Map.Entry<String, ArrayList<String>> entry : object.getFieldArray().entrySet()) {
			String key = entry.getKey();
			ArrayList<String> value = entry.getValue();
			System.out.printf("%s: [", key);
			
			for (int i = 0; i < value.size(); i++)
			{
				System.out.printf("%s", value.get(i));
				if (i + 1 != value.size())
					System.out.printf(", ");
			}
			
			System.out.printf("]\n");
		}
		
		// print out objects in json array
		for (Map.Entry<String, ArrayList<DataObject>> entry : object.getFieldArrayObject().entrySet()) {
			
			String key = entry.getKey();
			ArrayList<DataObject> value = entry.getValue();
			System.out.printf("%s\n", key);
			for (DataObject it : value)
				printObject(it);
		}
		
		// print out child objects
		for (Map.Entry<String, DataObject> entry : object.getChildObject().entrySet()) {
			
			String key = entry.getKey();
			DataObject value = entry.getValue();
			System.out.printf("%s\n", key);
			printObject(value);
			
		}
		
	}
	
	public static void parseArray(JSONObject json, String key, DataObject data)
	{
		JSONArray jsonArray = (JSONArray)json.get(key);
		if (jsonArray == null)
			return;
		
		ArrayList<DataObject> objectArray = new ArrayList<DataObject>();
		ArrayList<String> stringArray = new ArrayList<String>();
		
		for (int i = 0; i < jsonArray.size(); i++)
		{
			Object object = jsonArray.get(i);
			
			if (object instanceof JSONObject)
			{
				// json array contains an jsonobject
				// create a new data object and let parseObject handle it
				DataObject arrayObject = new DataObject();
				parseObject(arrayObject, (JSONObject)object);
				objectArray.add(arrayObject);
				continue;
			}
			
			stringArray.add((String)object);
		}
		
		if (objectArray.size() > 0)
			data.addDataArrayObject(key, objectArray);
		
		if (stringArray.size() > 0)
			data.addDataArray(key, stringArray);
		
	}
	
	public static void parseObject(DataObject data, JSONObject json)
	{
		
		Iterator<String> iter = json.keySet().iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			
			if (json.get(key) instanceof JSONObject) {
				DataObject child = new DataObject();
				data.addChild(key, child);
				parseObject(child, (JSONObject)json.get(key));
				continue;
			}
			
			if (json.get(key) instanceof JSONArray) {
				parseArray(json, key, data);
				continue;
			}
			
			String value = (String)json.get(key);
			data.addDataField(key, value);
			
		}
		
	}
	
}
