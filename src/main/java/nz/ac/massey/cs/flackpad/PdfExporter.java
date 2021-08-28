package nz.ac.massey.cs.flackpad;

import java.io.File;
import java.io.FileOutputStream;
import java.net.InetAddress;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

class PdfExporter {

	String export(String text, String appName, File file) throws Exception {
		// Make sure file has correct extension
		String filePath = file.getAbsolutePath();
		if (!filePath.endsWith(".pdf")) {
			filePath += ".pdf";
		}

		// Get document and writer
		Document document = new Document();
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));

		// Add meta and text to document
		document.open();
		document.addAuthor(InetAddress.getLocalHost().getHostName());
		document.addCreationDate();
		document.addCreator(appName);
		document.add(new Paragraph(text));
		document.close();

		// Close writer
		writer.close();
		
		// Return file name
		return filePath.substring(filePath.lastIndexOf("\\") + 1);
	}

}
