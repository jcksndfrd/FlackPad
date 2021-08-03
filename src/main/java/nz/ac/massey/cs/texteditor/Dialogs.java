package nz.ac.massey.cs.texteditor;

import javax.swing.*;

public final class Dialogs {
	
	private Dialogs() {
		throw new UnsupportedOperationException();
	}
	
	public static void error(String message, JFrame frame) {
		JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.WARNING_MESSAGE);
	}
	
	public static int saveWarning(TextEditor frame) {
		return JOptionPane.showOptionDialog(frame, "Do you want to save changes to " + frame.getFileName(),
				"Warning: Unsaved Changes", 0, JOptionPane.PLAIN_MESSAGE, null, new String[] { "Save", "Don't Save", "Cancel" }, null);
	}
}
