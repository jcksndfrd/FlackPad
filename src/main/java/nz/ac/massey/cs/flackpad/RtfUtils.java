package nz.ac.massey.cs.flackpad;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.rtf.RTFEditorKit;

final class RtfUtils {

	private RtfUtils() {
		throw new UnsupportedOperationException();
	}

	static String loadFile(File file) throws IOException, BadLocationException {
		// Get input stream, RTFEditorKit and a blank document to write to
		final RTFEditorKit kit = new RTFEditorKit();
		final Document document = kit.createDefaultDocument();
		try (FileInputStream stream = new FileInputStream(file)) {
			// Write read stream data to document
			kit.read(stream, document, 0);
		}

		// Return read text
		return document.getText(0, document.getLength());
	}

	static void export(String text, File file) throws IOException, BadLocationException {

		// Get RTFEditorKit and a blank document to write to
		final RTFEditorKit kit = new RTFEditorKit();
		final Document document = kit.createDefaultDocument();

		// Write text to document
		document.insertString(0, text, null);

		// Write document to output stream
		try (FileOutputStream stream = new FileOutputStream(file)) {
			kit.write(stream, document, 0, -1);
		}
	}
	
}
