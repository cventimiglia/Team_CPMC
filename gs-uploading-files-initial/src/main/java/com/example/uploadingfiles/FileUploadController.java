package com.example.uploadingfiles;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.uploadingfiles.fileParsing.FileParser;
import com.example.uploadingfiles.fileParsing.Parameter;
import com.example.uploadingfiles.storage.StorageFileNotFoundException;
import com.example.uploadingfiles.storage.StorageService;

//This file was imported using spring boot. We did not create this file, but simply modified it to fit our needs
//link to the source: https://spring.io/guides/gs/uploading-files/
//This class is used to decide routes for the API to take.
@Controller
public class FileUploadController {

	private final StorageService storageService;
	private final FileParser fileParser; // add this instance variable in order to use our custom methods

	// update the constructor to use our instance variable
	@Autowired
	public FileUploadController(StorageService storageService, FileParser fileParser) {
		this.storageService = storageService;
		this.fileParser = fileParser;
	}

	// This code remained unchanged. It is used to render our view when the page is
	// loaded.
	@GetMapping("/")
	public String listUploadedFiles(Model model) throws IOException {

		model.addAttribute("files",
				storageService.loadAll()
						.map(path -> MvcUriComponentsBuilder
								.fromMethodName(FileUploadController.class, "serveFile", path.getFileName().toString())
								.build().toUri().toString())
						.collect(Collectors.toList()));

		return "uploadForm";
	}

	// This code remained unchanged. This is to get a specific file resource stored
	// in our upload-dir folder.
	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	public void processFile(MultipartFile file, String fileNameNoExt) throws Exception {
		// store the json file in order to use it later
		storageService.store(file);

		// create the File object inside of the upload-dir location. Create the
		// BufferedWriter to write to the File
		File outputFile = new File("upload-dir/" + fileNameNoExt + "-combos.txt");
		BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

		// parse the file and create the count variable and update it as you populate
		// the arrayList
		ArrayList<Parameter> arrList = fileParser.parseFile("upload-dir/" + file.getOriginalFilename());
		int count = 1;

		// write to the file and update the count variable so it can be used later.
		for (Parameter temp : arrList) {
			writer.write(
					"Parameter Name: " + temp.getName() + " | Equivalence Classes: " + temp.getEquivalenceClasses());
			writer.write("\n");
			count = count * temp.getEquivalenceClasses().size();
		}

		writer.write("\n");

		// create a matrix and populate it using the createCombos method. Pass in
		// arrList and count as parameters
		String[][] combos = fileParser.createCombos(arrList, count);

		// this is used to write the test case combinations to the text file.
		int testCaseNumber = 1;
		for (int row = 0; row < combos.length; row++) {
			if (testCaseNumber < 10) {
				writer.write("Test Case " + testCaseNumber + ":   ");
			} else if (testCaseNumber >= 10 && testCaseNumber < 100) {
				writer.write("Test Case " + testCaseNumber + ":  ");
			} else {
				writer.write("Test Case " + testCaseNumber + ": ");
			}
			testCaseNumber++;
			for (int column = 0; column < combos[row].length; column++) {
				writer.write(combos[row][column] + " ");
			}
			writer.write("\n");
		}

		writer.close();

	}

	@PostMapping("/api")
	@ResponseBody
	public ResponseEntity<Resource> directAPI(@RequestParam("file") MultipartFile file) {
		String fileName = file.getOriginalFilename();
		int dotIndex = fileName.lastIndexOf(".");
		String fileNameNoExt = fileName.substring(0, dotIndex);
		
		try {
			processFile(file, fileNameNoExt);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return ResponseEntity.badRequest().body(null);
		}
		
		Resource res = storageService.loadAsResource(fileNameNoExt + "-combos.txt");
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + res.getFilename() + "\"")
				.body(res);
	}

	// This method was modified in order to gain the functionality we need.
	// The purpose of this method is to store the JSON file submitted in order to
	// use it
	// to parse and create combinations. Then it will write to a new file and add
	// that to the
	// upload-dir folder.
	@PostMapping("/")
	public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

		// create the text file name based on the JSON file
		String fileName = file.getOriginalFilename();
		int dotIndex = fileName.lastIndexOf(".");
		String fileNameNoExt = fileName.substring(0, dotIndex);

		try {
			processFile(file, fileNameNoExt);

			// catch and error and throw an error message. This gets rid of the server error
			// page
		} catch (Exception e) {
			// e.printStackTrace();
			redirectAttributes.addFlashAttribute("message", e.getMessage());
			return "redirect:/";
		}

		// return a message to indicate the file was successfully submitted and that
		// they recieved their text file.
		redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "\n"
				+ "and recieved " + fileNameNoExt + "-combos.txt!");

		return "redirect:/";
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}

}
