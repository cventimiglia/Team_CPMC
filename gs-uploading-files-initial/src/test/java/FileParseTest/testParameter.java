package FileParseTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.example.uploadingfiles.fileParsing.Parameter;

/**
 * Class: testParameter
 * @author Cameron Ventimiglia
 * Version: 1.0
 * Written: October 31, 2021
 * Course: ITEC 3870
 * 
 * Purpose: To test the methods inside of the Parameter Class
 */
class testParameter {

	@Test
	void testNameGetters_Setters() {
		Parameter param = new Parameter();
		
		param.setName("Numbers");
		
		assertEquals("Numbers", param.getName(), "getName and setName are wrong");	
	}
	
	@Test
	void testEquivClassGetters_Setters() {
		Parameter param = new Parameter();
		
		ArrayList<String> arrList = new ArrayList<>();
		arrList.add("One");
		arrList.add("Two");
		
		param.setEquivalenceClasses(arrList);
		
		ArrayList<String> list = param.getEquivalenceClasses();
		
		assertEquals(list, param.getEquivalenceClasses(), "getters and setters for equivalenceClass list are wrong");

	}
	
	@Test
	void testAddParam() {
		Parameter param = new Parameter();
		
		ArrayList<String> arrList = new ArrayList<>();
		arrList.add("One");
		arrList.add("Two");
		
		param.setName("Numbers");
		param.setEquivalenceClasses(arrList);
		
		ArrayList<Parameter> paramList = new ArrayList<>();
		
		paramList.add(param);
		
		assertEquals(param.getName(), paramList.get(0).getName(), "addParams method is wrong");
	}

}
