package nz.ac.massey.cs.flackpad;

import java.awt.Component;

import javax.swing.JOptionPane;

final class DialogUtils {

	private DialogUtils() {
		throw new UnsupportedOperationException();
	}

	public static void message(String message, Component parent) {
		JOptionPane.showMessageDialog(parent, message, "Info", JOptionPane.INFORMATION_MESSAGE);
	}

	static void error(String message, Component parent) {
		JOptionPane.showMessageDialog(parent, message, "Error", JOptionPane.WARNING_MESSAGE);
	}

	static void warning(String message, Component parent) {
		JOptionPane.showMessageDialog(parent, message, "Warning", JOptionPane.WARNING_MESSAGE);
	}

	static int saveWarning(String fileName, Component parent) {
		return JOptionPane.showOptionDialog(parent, "Do you want to save changes to " + fileName,
				"Warning: Unsaved Changes", 0, JOptionPane.PLAIN_MESSAGE, null,
				new String[] { "Save", "Don't Save", "Cancel" }, null);
	}

	static int overwriteWarning(String fileName, Component parent) {
		return JOptionPane.showConfirmDialog(parent, fileName + " already exists.\nDo you want to overwrite it?",
				"Confirm File Overwrite", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
	}
}
