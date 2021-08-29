package nz.ac.massey.cs.flackpad;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

final class PdfUtils {
	
	private PdfUtils() {
		throw new UnsupportedOperationException();
	}
	
	static void export(String text, String appName, File file) throws FileNotFoundException, DocumentException, UnknownHostException {

		// Get document and writer
		final Document document = new Document();
		final PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));

		// Add meta and text to document
		document.open();
		document.addAuthor(InetAddress.getLocalHost().getHostName());
		document.addCreationDate();
		document.addCreator(appName);
		document.add(new Paragraph(text));
		document.close();

		// Close writer
		writer.close();
	}

}
