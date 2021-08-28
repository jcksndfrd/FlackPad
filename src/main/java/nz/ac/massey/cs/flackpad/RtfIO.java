package nz.ac.massey.cs.flackpad;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.swing.text.Document;
import javax.swing.text.rtf.RTFEditorKit;

class RtfIO {

	String loadFile(File file) throws Exception {
		String text = "";

		// Get input stream, RTFEditorKit and a blank document to write to
		FileInputStream stream = new FileInputStream(file);
		RTFEditorKit kit = new RTFEditorKit();
		Document document = kit.createDefaultDocument();

		// Write read stream data to document
		kit.read(stream, document, 0);
		// Get text from document
		text = document.getText(0, document.getLength());

		// Close stream and return read text
		stream.close();
		return text;
	}

	String export(String text, File file) throws Exception {
		// Make sure file has correct extension
		String filePath = file.getAbsolutePath();
		if (!filePath.endsWith(".rtf")) {
			filePath += ".rtf";
		}

		// Get output stream, RTFEditorKit and a blank document to write to
		FileOutputStream stream = new FileOutputStream(filePath);
		RTFEditorKit kit = new RTFEditorKit();
		Document document = kit.createDefaultDocument();
		
		// Write text to document
		document.insertString(0, text, null);
		
		// Write document to output stream
		kit.write(stream, document, 0, -1);
		// CLose output stream
		stream.close();

		// Return file name
		return filePath.substring(filePath.lastIndexOf("\\") + 1);
	}
}
