package nz.ac.massey.cs.flackpad;

import java.awt.Component;
import java.io.FileOutputStream;
import java.net.InetAddress;

import javax.swing.JFileChooser;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

class PdfExporter {
	
	private String appName;
	private Component parent;
	
	PdfExporter(String appName, Component parent) {
		this.appName = appName;
		this.parent = parent;
	}

	void export(String text) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Save As");
		if (fileChooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
			Document document = new Document();
			String filePath = fileChooser.getSelectedFile().getAbsolutePath();
			if (!filePath.endsWith(".pdf")) filePath += ".pdf";
			
			try {
				PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
				
				document.open();
				document.addAuthor(InetAddress.getLocalHost().getHostName());
				document.addCreationDate();
				document.addCreator(appName);
				document.add(new Paragraph(text));
				document.close();
				
				writer.close();
				Dialogs.message("Succesfully exported as \"" + filePath.substring(filePath.lastIndexOf("\\")+1) + "\"", parent);
			} catch (Exception e) {
				Dialogs.error("Something went wrong exporting to PDF", parent);
			}
		}
	}

}
