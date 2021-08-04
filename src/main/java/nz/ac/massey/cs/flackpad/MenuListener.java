package nz.ac.massey.cs.flackpad;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class MenuListener implements ActionListener {

	private Window window;

	MenuListener(Window window) {
		this.window = window;
	}

	public void actionPerformed(ActionEvent e) {

		switch (e.getActionCommand()) {
		case "New":
			window.newDoc();
			break;

		case "New Window":
			// Load a new text editor instance
			new Window();
			break;

		case "Open":
			FileIO.open(window);
			break;

		case "Save":
			FileIO.save(window);
			break;

		case "Save As":
			FileIO.saveAs(window);
			break;
		case "Exit":
			window.exit();
		}
	}
}
