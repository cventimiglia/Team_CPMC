package fileParse;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class DataObject {

	private HashMap<String, String> field;
	private HashMap<String, ArrayList<String>> fieldArray;
	private HashMap<String, ArrayList<DataObject>> fieldArrayObject;
	private HashMap<String, DataObject> childObject;

	public DataObject() {
		field = new HashMap<String, String>();
		fieldArray = new HashMap<String, ArrayList<String>>();
		fieldArrayObject = new HashMap<String, ArrayList<DataObject>>();
		childObject = new HashMap<String, DataObject>();
	}

	private DataObject findObjectInChild(DataObject child, String key) {
		if (child == null)
			return null;

		DataObject object = child.childObject.get(key);
		if (object != null)
			return object;

		for (Map.Entry<String, DataObject> entry : child.getChildObject().entrySet()) {
			DataObject value = entry.getValue();
			if (key.equals(entry.getKey()))
				return value;
			
			object = findObjectInChild(value, key);
			if (object != null)
				return object;
		}
		
		return null;
		
	}

	@SuppressWarnings("unchecked")
	public <T> T find(String key) {
		String string = field.get(key);
		if (string != null && string.length() != 0)
			return (T)string;

		ArrayList<String> stringArray = fieldArray.get(key);
		if (stringArray != null && stringArray.size() != 0)
			return (T)stringArray;

		ArrayList<DataObject> objectArray = fieldArrayObject.get(key);
		if (objectArray != null && objectArray.size() != 0)
			return (T)objectArray;
		
		for (Map.Entry<String, DataObject> entry : childObject.entrySet()) {
			DataObject value = entry.getValue();
			if (key.equals(entry.getKey()))
				return (T)value;
			
			DataObject object = findObjectInChild(value, key);
			if (object != null)
				return (T)object;
		}

		return null;
	}

	public void addDataField(String key, String value) {
		field.put(key, value);
	}

	public void addDataArray(String key, ArrayList<String> data) {
		fieldArray.put(key, data);
	}

	public void addDataArrayObject(String key, ArrayList<DataObject> data) {
		fieldArrayObject.put(key, data);
	}

	public void addChild(String key, DataObject object) {
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
