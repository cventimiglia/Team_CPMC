package com.example.uploadingfiles;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
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

import com.example.uploadingfiles.fileParsing.OldFileParser;
import com.example.uploadingfiles.fileParsing.Parameter;
import com.example.uploadingfiles.storage.StorageFileNotFoundException;
import com.example.uploadingfiles.storage.StorageService;

@Controller
public class FileUploadController {

	private final StorageService storageService;
	private final OldFileParser oldFileParser;

	@Autowired
	public FileUploadController(StorageService storageService, OldFileParser oldFileParser) {
		this.storageService = storageService;
		this.oldFileParser = oldFileParser;
	}
	

	@GetMapping("/")
	public String listUploadedFiles(Model model) throws IOException {

		model.addAttribute("files", storageService.loadAll().map(
				path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
						"serveFile", path.getFileName().toString()).build().toUri().toString())
				.collect(Collectors.toList()));

		return "uploadForm";
	}


	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
		Resource file = storageService.loadAsResource(filename);		
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}
	

	@PostMapping("/")
	public String handleFileUpload(@RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes) {
		
		
		storageService.store(file);

		String fileName = file.getOriginalFilename();
		int dotIndex = fileName.lastIndexOf(".");
		String fileNameNoExt = fileName.substring(0, dotIndex);


		try {
		File outputFile = new File(fileNameNoExt + "-combos.txt");
		BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

		ArrayList<Parameter> arrList = oldFileParser.parseFile("upload-dir/" + file.getOriginalFilename());
		int count = 1;

		for (Parameter temp : arrList) {
		writer.write("Parameter Name: " + temp.getName() + " | Equivalence Classes: " + temp.getEquivalenceClasses());
		writer.write("\n");
		count = count * temp.getEquivalenceClasses().size();
		}

		writer.write("\n");

		String[][] combos = oldFileParser.createCombos(arrList, count);
		for (int row = 0; row < combos.length; row++) {
		for (int column = 0; column < combos[row].length; column++) {
		writer.write(combos[row][column] + " ");

		}

		writer.write("\n");
		}

		writer.close();
		storageService.store(outputFile);

		} catch (Exception e) {
		e.printStackTrace();
		}



		redirectAttributes.addFlashAttribute("message",
		"You successfully uploaded " + file.getOriginalFilename() +"\n"
		+ "and recieved " + fileNameNoExt + "-combos.txt!");

		return "redirect:/";
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}

}
