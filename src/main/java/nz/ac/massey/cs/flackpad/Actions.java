package nz.ac.massey.cs.flackpad;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.print.PrinterJob;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUI;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;

import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

public class Actions {
	public static void performPaste(Window windowInstance) {
		try {
			TextArea textArea = windowInstance.getTextArea();
			/*
			 * Handle 2 cases: a. Paste with text selected b. Paste with no text selected
			 */
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			Transferable data = clipboard.getContents(null);
			String plaintext = (String) data.getTransferData(DataFlavor.stringFlavor);

			if (textArea.getSelectedText() != null) {
				// .a
				textArea.replaceSelection(plaintext);
			} else {
				// .b
				textArea.insert(plaintext, textArea.getCaretPosition());
			}

		} catch (Exception err) {
			Dialogs.error("Could not paste from clipboard", windowInstance);
		}

	}

	public static void performCopy(Window windowInstance) {
		try {
			TextArea textArea = windowInstance.getTextArea();
			StringSelection stringSelection = new StringSelection(textArea.getSelectedText());
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(stringSelection, null);
		} catch (Exception err) {
			Dialogs.error("Could not copy selected text to clipboard", windowInstance);
		}
	}

	public static void performCut(Window windowInstance) {
		try {
			TextArea textArea = windowInstance.getTextArea();
			StringSelection stringSelection = new StringSelection(textArea.getSelectedText());
			textArea.replaceSelection("");
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(stringSelection, null);
		} catch (Exception err) {
			Dialogs.error("Could not cut selected", windowInstance);
		}
	}

	public static void performSave(Window windowInstance) {
		FileIO.save(windowInstance);
	}

	public static void performSaveAs(Window windowInstance) {
		FileIO.saveAs(windowInstance);
	}

	public static void performNewWindow(Window windowInstance) {
		new Window();
	}

	public static void performNew(Window windowInstance) {
		windowInstance.newDoc();
	}

	public static void performOpen(Window windowInstance) {
		FileIO.open(windowInstance);
	}

	public static void performExit(Window windowInstance) {
		windowInstance.exit();
	}

	public static void performSelectAll(Window windowInstance) {
		try {
			TextArea textArea = windowInstance.getTextArea();
			textArea.setSelectionStart(0);
			textArea.setSelectionEnd(textArea.getText().length());
		} catch (Exception err) {
			Dialogs.error("Could not select all", windowInstance);
    	}
	}

	public static void performFind(Window windowInstance) {
		// Show find / replace bar
		windowInstance.showFindBar();
	}	
	public static void performEscapeFind(Window windowInstance) {
		try {
			if (windowInstance.getFindField().isVisible()) {
				windowInstance.hideFindBar();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Dialogs.error("Could not escape search bar", windowInstance);
		}
	}	
	public static void performDelete(Window windowInstance) {
		windowInstance.getTextArea().replaceSelection(null);
	}

	public static void performPrint(Window windowInstance) {
		try {
			DocFlavor docfavour = javax.print.DocFlavor.BYTE_ARRAY.AUTOSENSE;
			
			// Get available printers
			PrintService[] allPrinters = PrintServiceLookup.lookupPrintServices(docfavour, null);
			PrintService preferredPrinter = PrintServiceLookup.lookupDefaultPrintService();
			PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
			
			// Get printer choice from user
			PrintService selection = ServiceUI.printDialog(null, 100, 100, allPrinters, preferredPrinter, null, aset); 
			if (selection == null) {
				return; // User chose not to print
			}
				
			DocPrintJob job = selection.createPrintJob();
			String data = windowInstance.getTextArea().getText();
						 
			Doc doc = new SimpleDoc(data.getBytes(), docfavour, null);
			PrintRequestAttributeSet attrs = new HashPrintRequestAttributeSet();
			attrs.add(new Copies(1));
			
			// Option 1 - doesn't print
			//job.print(doc, attrs); 
			
			// Option 2
			InputStream stream = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
			Actions.printer(selection, null, attrs);
						
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Issue with printing document");
		}
	}
	
	private static boolean printer(PrintService printService, InputStream inputStream, PrintRequestAttributeSet attributes)
		    throws Exception {
		    try {
		        PDDocument pdf = PDDocument.load(inputStream);
		        PrinterJob job = PrinterJob.getPrinterJob();
		        job.setPrintService(printService);
		        PDFPageable pageable = new PDFPageable(pdf);
		        job.setPageable(pageable); // Problematic line of code
		        job.print(attributes);
		    } 
		    catch (Exception e) {
		    	e.printStackTrace();
		    }
		    return true;
		}

	public static void performPDFExport(Window window) {
		FileIO.PDFExport(window);
	}
}
