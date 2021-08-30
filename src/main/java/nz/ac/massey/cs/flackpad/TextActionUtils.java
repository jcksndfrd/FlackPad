package nz.ac.massey.cs.flackpad;

import java.awt.AWTError;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

final class TextActionUtils {

	private TextActionUtils() {
		throw new UnsupportedOperationException();
	}

	static void performPaste(Window window) {
		final TextArea textArea = window.getTextArea();
		try {
			/*
			 * Handle 2 cases: a. Paste with no text selected b. Paste with text selected
			 */
			final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			final Transferable data = clipboard.getContents(null);
			final String plaintext = (String) data.getTransferData(DataFlavor.stringFlavor);

			if (textArea.getSelectedText() == null) {
				// .a
				textArea.insert(plaintext, textArea.getCaretPosition());
			} else {
				// .b
				textArea.replaceSelection(plaintext);
			}

		} catch (UnsupportedFlavorException | IOException err) {
			// Change to error dialog
			DialogUtils.warning("Could not paste from clipboard", textArea);
		}

	}

	static void performCopy(Window window) {
		final TextArea textArea = window.getTextArea();
		try {
			final StringSelection stringSelection = new StringSelection(textArea.getSelectedText());
			final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(stringSelection, null);
		} catch (AWTError | IllegalStateException err) {
			// Change to error dialog
			DialogUtils.warning("Could not copy selected text to clipboard", textArea);
		}
	}

	static void performCut(Window window) {
		final TextArea textArea = window.getTextArea();
		try {
			final StringSelection stringSelection = new StringSelection(textArea.getSelectedText());
			textArea.replaceSelection("");
			final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(stringSelection, null);
		} catch (AWTError | IllegalStateException err) {
			// Change to error dialog
			DialogUtils.warning("Could not cut selected text to clipboard", textArea);
		}
	}

	static void performSelectAll(Window window) {
		final TextArea textArea = window.getTextArea();
		textArea.setSelectionStart(0);
		textArea.setSelectionEnd(textArea.getText().length());
	}

	static void performFind(Window window) {
		// Show find / replace bar
		window.showFindBar();
	}

	static void performEscapeFind(Window window) {
		if (window.getFindField().isVisible()) {
			window.hideFindBar();
		}
	}

	static void performFindString(Window window) {
		if (window.getFindField().isVisible()) {
			window.hideFindBar();
		}
	}

	static void performDelete(Window window) {
		window.getTextArea().replaceSelection(null);
	}

}
