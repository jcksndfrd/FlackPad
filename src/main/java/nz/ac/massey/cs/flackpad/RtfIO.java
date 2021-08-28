package nz.ac.massey.cs.flackpad;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.rtf.RTFEditorKit;

class RtfIO {
	
	String loadFile (File file) throws IOException {
		String text = "";
		
		// Get input stream, RTFEditorKit and a blank document to write to
		FileInputStream stream = new FileInputStream(file);
		RTFEditorKit kit = new RTFEditorKit();
		Document document = kit.createDefaultDocument();
		
		try {
			// Write read stream data to document
			kit.read(stream, document, 0);
			// Get text from document
			text = document.getText(0, document.getLength());
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		
		// Close stream and return read text
		stream.close();
		return text;
	}
	
	String export(String text, File file) {
		String filePath = file.getAbsolutePath();
		if (!filePath.endsWith(".rtf")) {
			filePath += ".rtf";
		}
		

		return filePath.substring(filePath.lastIndexOf("\\") + 1);
	}
}
