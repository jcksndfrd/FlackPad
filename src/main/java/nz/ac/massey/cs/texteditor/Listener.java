package nz.ac.massey.cs.texteditor;

import java.awt.event.*;

public class Listener implements ActionListener {

	private TextEditor frame;

	public Listener(TextEditor frame) {
		this.frame = frame;
	}

	public void actionPerformed(ActionEvent e) {

		switch (e.getActionCommand()) {
		case "New":
			// 1. Check if the current file is saved
			// Saved --> Create a new file
			// Unsaved --> Force user to save file

			// Load a new file into the current window
			break;

		case "New Window":
			// Load a new text editor instance
			new TextEditor();
			break;

		case "Open":
			FileIO.open(frame);
			break;

		case "Save":
			FileIO.save(frame);
			break;

		case "Save As":
			FileIO.saveAs(frame);
			break;
		}
	}
}
