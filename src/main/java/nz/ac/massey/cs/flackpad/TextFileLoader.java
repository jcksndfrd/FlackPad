package nz.ac.massey.cs.flackpad;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

final class TextFileLoader {

	private TextFileLoader() {
		throw new UnsupportedOperationException();
	}

	static String loadFile(File file) throws IOException {
//		window.getTextArea().setText("");
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String text = "";
		String line = reader.readLine();

		while (line != null) {
			text += line;
			if ((line = reader.readLine()) != null)
				text += System.getProperty("line.separator");
		}

		reader.close();

		return text;
	}

}