package fileParse;
import java.util.HashMap;
import java.util.ArrayList;

public class DataObject {

	private HashMap<String, String> field;
	private HashMap<String, ArrayList<String>> fieldArray;
	private HashMap<String, ArrayList<DataObject>> fieldArrayObject;
	private HashMap<String, DataObject> childObject;
	
	public DataObject()
	{
		field = new HashMap<String, String>();
		fieldArray = new HashMap<String, ArrayList<String>>();
		fieldArrayObject = new HashMap<String, ArrayList<DataObject>>();
		childObject = new HashMap<String, DataObject>();
	}
	
	public void addDataField(String key, String value)
	{
		field.put(key, value);
	}
	
	public void addDataArray(String key, ArrayList<String> data)
	{
		fieldArray.put(key, data);
	}
	
	public void addDataArrayObject(String key, ArrayList<DataObject> data)
	{
		fieldArrayObject.put(key, data);
	}
	
	public void addChild(String key, DataObject object)
	{
		childObject.put(key, object);
	}
	
	public HashMap<String, String> getField() {
		return field;
	}

	public HashMap<String, ArrayList<String>> getFieldArray() {
		return fieldArray;
	}

	public HashMap<String, ArrayList<DataObject>> getFieldArrayObject() {
		return fieldArrayObject;
	}

	public HashMap<String, DataObject> getChildObject() {
		return childObject;
	}
	
}
