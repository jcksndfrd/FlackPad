package nz.ac.massey.cs.flackpad;

import java.io.File;
import java.io.FileOutputStream;
import java.net.InetAddress;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

class PdfExporter {

	String export(String text, String appName, File file) throws Exception {
		String filePath = file.getAbsolutePath();
		if (!filePath.endsWith(".pdf")) {
			filePath += ".pdf";
		}

		Document document = new Document();
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));

		document.open();
		document.addAuthor(InetAddress.getLocalHost().getHostName());
		document.addCreationDate();
		document.addCreator(appName);
		document.add(new Paragraph(text));
		document.close();

		writer.close();
		
		return filePath.substring(filePath.lastIndexOf("\\") + 1);
	}

}
