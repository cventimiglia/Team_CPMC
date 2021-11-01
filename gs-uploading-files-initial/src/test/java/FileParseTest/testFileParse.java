package FileParseTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.example.uploadingfiles.fileParsing.FileParser;
import com.example.uploadingfiles.fileParsing.Parameter;
import com.example.uploadingfiles.storage.StorageException;

/**
 * Class: testFileParse
 * @author Cameron Ventimiglia
 * Version: 1.0
 * Written: October 31, 2021
 * Course: ITEC 3870
 * 
 * Purpose: To test the methods inside of the FileParse Class
 */
class testFileParse {

	@Test
	void testParseFile() {

		FileParser fileParser = new FileParser();
		
		try {
			ArrayList<Parameter> arrList = fileParser.parseFile("ExecutionQueueOnSave.json");
			assertTrue(arrList.size() > 0, "error parsing the file");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testCreateCombos() {
		FileParser fileParser = new FileParser();
			
		try {
			int count = 1;
			ArrayList<Parameter> arrList = fileParser.parseFile("ExecutionQueueOnSave.json");
			
			for (Parameter temp : arrList) {
				count = count * temp.getEquivalenceClasses().size();
			}
			
			String[][] combos = fileParser.createCombos(arrList, count);
			assertTrue(combos.length > 0, "error creating matrix");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testCountParameter() {
		FileParser fileParser = new FileParser();
		
		try {
			int count = 1;
			ArrayList<Parameter> arrList = fileParser.parseFile("ExecutionQueueOnSave.json");
			
			for (Parameter temp : arrList) {
				count = count * temp.getEquivalenceClasses().size();
			}
			
			assertTrue(count == 16, "error updating parameter: count");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void checkFileTypeLimitationError() {
		String filename = "testFile.txt";
		int lastIndext = filename.lastIndexOf('.');
		String extension = filename.substring(lastIndext, filename.length());
		
		assertThrows(StorageException.class, () -> {
			if (!(extension).equalsIgnoreCase(".json")) {
				throw new StorageException("Failed to store non-json file");
			}
		});
	}
}
