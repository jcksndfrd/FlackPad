package nz.ac.massey.cs.flackpad;

import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class TestFileIO {
	private final Window window = new Window();
	private final FileIO fileIO = new FileIO(window);
	
	@AfterEach
	void clearTextArea() {
		window.setText("");
	}
	
	@Test
	void loadFileTest() {
		
		String nl = System.getProperty("line.separator");
		String testText = "This"+nl+"Is"+nl+"A"+nl+"Test";
		
		try {
			// Create temp file
			File tempFile = File.createTempFile("temp", ".txt");
			FileWriter writer = new FileWriter(tempFile);
			writer.write(testText);
			writer.close();
			
			// Load temp file
			fileIO.loadFile(tempFile);
			
			// Delete temp file
			tempFile.delete();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Check it loaded correctly
		Assertions.assertEquals(window.getText(), testText);
	}
	
	@Test
	void saveFileTest() {
		
		String nl = System.getProperty("line.separator");
		String testText = "This"+nl+"Is"+nl+"A"+nl+"Test";
		String readText = "";
		
		// Set text area text
		window.setText(testText);

		try {
			// Save to temp file
			File tempFile = File.createTempFile("temp", ".txt");
			fileIO.saveFile(tempFile);
			
			// Read file
			readText = new String(Files.readAllBytes(tempFile.toPath()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Check it saved correctly
		Assertions.assertEquals(readText, testText);
	}

}
