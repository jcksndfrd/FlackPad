package nz.ac.massey.cs.flackpad;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

import javax.swing.JScrollPane;

public class Actions {

	public static void performPaste(Window window) {
		try {
			TextArea textArea = window.getTextArea();
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
			Dialogs.error("Could not paste from clipboard", window.getFrame());
		}

	}

	public static void performCopy(Window window) {
		try {
			TextArea textArea = window.getTextArea();
			StringSelection stringSelection = new StringSelection(textArea.getSelectedText());
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(stringSelection, null);
		} catch (Exception err) {
			Dialogs.error("Could not copy selected text to clipboard", window.getFrame());
		}
	}

	public static void performCut(Window window) {
		try {
			TextArea textArea = window.getTextArea();
			StringSelection stringSelection = new StringSelection(textArea.getSelectedText());
			textArea.replaceSelection("");
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(stringSelection, null);
		} catch (Exception err) {
			Dialogs.error("Could not cut selected", window.getFrame());
		}
	}

	public static void performSelectAll(Window window) {
		try {
			TextArea textArea = window.getTextArea();
			textArea.setSelectionStart(0);
			textArea.setSelectionEnd(textArea.getText().length());
		} catch (Exception err) {
			Dialogs.error("Could not select all", window.getFrame());
		}
	}

	public static void performFind(Window window) {
		// Show find / replace bar
		window.showFindBar();
	}

	public static void performEscapeFind(Window window) {
		try {
			if (window.getFindField().isVisible()) {
				window.hideFindBar();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Dialogs.error("Could not escape search bar", window.getFrame());
		}
	}

	public static void performDelete(Window window) {
		window.getTextArea().replaceSelection(null);
	}

	public static void performPrint(Window window) {
		// Stub
	}
}
