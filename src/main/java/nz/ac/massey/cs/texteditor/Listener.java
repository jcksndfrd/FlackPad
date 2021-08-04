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
			frame.newDoc();
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
		case "Exit":
			frame.exit();
		}
	}
	
}
