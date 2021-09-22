package fileParse;

import java.util.ArrayList;

public class Parameter {
	private String name;
	private ArrayList<String> equivalenceClasses;
	
	public Parameter() {
		this.name = "";
		this.equivalenceClasses = new ArrayList<String>();
	}
	
	public void addParam(String equivClassName) {
		equivalenceClasses.add(equivClassName);
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<String> getEquivalenceClasses() {
		return equivalenceClasses;
	}

	public void setEquivalenceClasses(ArrayList<String> equivalenceClasses) {
		this.equivalenceClasses = equivalenceClasses;
	}
	
	
}
