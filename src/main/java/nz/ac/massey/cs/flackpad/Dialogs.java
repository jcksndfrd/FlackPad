package nz.ac.massey.cs.flackpad;

import javax.swing.*;

final class Dialogs {
	
	private Dialogs() {
		throw new UnsupportedOperationException();
	}

	public static void message(String message, Window window) {
		JOptionPane.showMessageDialog(window, message, "Info", JOptionPane.INFORMATION_MESSAGE);
	}
	
	static void error(String message, Window window) {
		JOptionPane.showMessageDialog(window, message, "Error", JOptionPane.WARNING_MESSAGE);
	}
	
	static void warning(String message, Window window) {
		JOptionPane.showMessageDialog(window, message, "Warning", JOptionPane.WARNING_MESSAGE);
	}
	
	static int saveWarning(Window window) {
		return JOptionPane.showOptionDialog(window, "Do you want to save changes to " + window.getFileName(),
				"Warning: Unsaved Changes", 0, JOptionPane.PLAIN_MESSAGE, null, new String[] { "Save", "Don't Save", "Cancel" }, null);
	}
	
	static void about(Window window) {
		JOptionPane.showMessageDialog(window, "We're pretty cool\n- Fletch and Jack", "About FlackPad", JOptionPane.INFORMATION_MESSAGE);
	}
	
}
