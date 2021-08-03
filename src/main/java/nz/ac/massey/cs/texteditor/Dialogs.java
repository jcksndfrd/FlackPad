package nz.ac.massey.cs.texteditor;

import javax.swing.*;

public final class Dialogs {
	
	private Dialogs() {
		throw new UnsupportedOperationException();
	}
	
	public static void error(String message, JFrame frame) {
		JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.WARNING_MESSAGE);
	}
}
