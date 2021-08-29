package nz.ac.massey.cs.flackpad;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

class Actions {

	static void performPaste(Window window) {
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
			System.out.println("Actions\\performPaste() - Error: Could not paste from clipboard");
		}

	}

	static void performCopy(Window window) {
		try {
			TextArea textArea = window.getTextArea();
			StringSelection stringSelection = new StringSelection(textArea.getSelectedText());
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(stringSelection, null);
		} catch (Exception err) {
			System.out.println("Actions\\performCopy() - Error: Could not copy selected text to clipboard");
		}
	}

	static void performCut(Window window) {
		try {
			TextArea textArea = window.getTextArea();
			StringSelection stringSelection = new StringSelection(textArea.getSelectedText());
			textArea.replaceSelection("");
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(stringSelection, null);
		} catch (Exception err) {
			System.out.println("Actions\\performCut() - Error: Could not cut selected text");
		}
	}

	static void performSelectAll(Window window) {
		try {
			TextArea textArea = window.getTextArea();
			textArea.setSelectionStart(0);
			textArea.setSelectionEnd(textArea.getText().length());
		} catch (Exception err) {
			System.out.println("Actions\\performSelectAll() - Error: Could not select all text");
		}
	}

	static void performFind(Window window) {
		// Show find / replace bar
		window.showFindBar();
	}

	static void performEscapeFind(Window window) {
		try {
			if (window.getFindField().isVisible()) {
				window.hideFindBar();
			}
		} catch (Exception e) {
			System.out.println("Actions\\performEscapeFind() - Error: Could not escape find");
		}
	}

	static void performFindString(Window window) {
		try {
			if (window.getFindField().isVisible()) {
				window.hideFindBar();
			}
		} catch (Exception e) {
			System.out.println("Actions\\performCopy() - Error: Could not search for string");
		}
	}

	static void performDelete(Window window) {
		try {
			window.getTextArea().replaceSelection(null);
		} catch (Exception e) {
			System.out.println("Actions\\performDelete() - Error: Could not delete text");
		}
	}

}
