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
		//File menu
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
		case "Print":
			break;
		case "Exit":
			window.exit();
			break;
		
		//Edit menu
		case "Cut":
			break;
		case "Copy":
			break;
		case "Paste":
			break;
		case "Select All":
			break;
		case "Delete":
			break;
		case "Find":
			break;
		case"Time and Date":
			window.getTextArea().addTimeAndDate();
		
		//Help menu
		case "About":
			break;
		}
	}
}
