package com.example.uploadingfiles.fileParsing;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

/**
 * Class: FileParser
 * Version: 1.0
 * Course: ITEC 3870
 * 
 * Purpose: The purpose of this class is to parse a json file into a hierarchical data structure for easy access
 * Behaves similar to a singly-linked list
 */
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

	/**
	 * method: findObjectInChild
	 * This method finds the object by key name in child objects recursively, returns null if not found
	 * @param DataObject(the child object), key(key name in child object)
	 * @return DataObject
	 */
	private DataObject findObjectInChild(DataObject child, String key) {
		if (child == null)
			return null;

		DataObject object = child.childObject.get(key);
		if (object != null)
			return object;

		// loop through map of child objects
		for (Map.Entry<String, DataObject> entry : child.getChildObject().entrySet()) {
			DataObject value = entry.getValue();
			if (key.equals(entry.getKey())) // key found, return the matching object
				return value;
			
			// key is not found, find it in child objects of the current object
			object = findObjectInChild(value, key);
			
			// return the object when found
			if (object != null)
				return object;
		}
		
		return null;
		
	}

	/**
	 * method: find
	 * This method finds the object by key name, returns null if not found
	 * @param key(key name in json object)
	 * @return returns a template type, it can only be String, ArrayList<String>, ArrayList<DataObject>, or DataObject
	 */
	@SuppressWarnings("unchecked")
	public <T> T find(String key) {
		
		// try finding it as string
		String string = field.get(key);
		if (string != null && string.length() != 0)
			return (T)string;

		// try finding it as arraylist of string
		ArrayList<String> stringArray = fieldArray.get(key);
		if (stringArray != null && stringArray.size() != 0)
			return (T)stringArray;
		
		// try finding it as arraylist of objects
		ArrayList<DataObject> objectArray = fieldArrayObject.get(key);
		if (objectArray != null && objectArray.size() != 0)
			return (T)objectArray;
		
		// non of the types were found, look for it in child recursively
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
