package nz.ac.massey.cs.flackpad;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Area;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

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
		TextArea area = windowInstance.getTextArea();
		
		// Show field with close button
		JTextField findfield = windowInstance.getFindField();
		findfield.setVisible(true);
		JButton findclose = windowInstance.getFindClose();
		findclose.setVisible(true);
		
		findclose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Hide items for find / replace
				findclose.setVisible(false);
				findfield.setVisible(false);
				// Remove all current highlights
		    	area.getHighlighter().removeAllHighlights();

			}
		});
		findfield.requestFocus();
		
		// Set search listener
		findfield.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {		
				String searchtext = findfield.getText();
				String text = area.getText();
				
				int offset = text.indexOf(searchtext);
				int length = searchtext.length();
				
				// Remove all current highlights
		    	area.getHighlighter().removeAllHighlights();
		    
				Highlighter.HighlightPainter painter = 
					    new DefaultHighlighter.DefaultHighlightPainter( Color.cyan );
				
				// Get all occurrences
				while ( offset != -1)
				{
				    try
				    {
				    	area.getHighlighter().addHighlight(offset, offset + length, painter);
				        offset = text.indexOf(searchtext, offset + 1);

				    }
				    catch(Exception e1) { 
						Dialogs.error("Could not highlight search phrase", windowInstance);
				    }
				}
				
			}
		});
		
	}	
	public static void performDelete(Window windowInstance) {
		// Stub
	}

	public static void performPrint(Window windowInstance) {
		// Stub
	}

	public static void performPDFExport(Window window) {
		FileIO.PDFExport(window);
	}
}
