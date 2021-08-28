package nz.ac.massey.cs.flackpad;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

class PlainTextIO {

	String loadFile(File file) throws IOException {
		// Get file reader
		BufferedReader reader = new BufferedReader(new FileReader(file));
		
		// Read lines
		String text = "";
		String line = reader.readLine();
		while (line != null) {
			text += line;
			if ((line = reader.readLine()) != null)
				text += System.getProperty("line.separator");
		}

		// Close reader
		reader.close();

		// Return read text
		return text;
	}

	void saveFile(String text, File file) throws IOException {
		// Save text to file
		BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
		writer.write(text);
		writer.flush();
		writer.close();
	}

}
