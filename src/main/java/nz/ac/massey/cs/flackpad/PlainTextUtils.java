package nz.ac.massey.cs.flackpad;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

final class PlainTextUtils {

	private PlainTextUtils() {
		throw new UnsupportedOperationException();
	}

	static String loadFile(File file) throws IOException {
		String text = "";

		// Get file reader
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			// Read lines
			String line = reader.readLine();
			while (line != null) {
				text += line;
				line = reader.readLine();
				if (line != null) {
					text += System.getProperty("line.separator");
				}
			}
		}

		// Return read text
		return text;
	}

	static void saveFile(String text, File file) throws IOException {
		// Save text to file
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
			writer.write(text);
			writer.flush();
		}
	}

}
