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
			Actions.performNew(window);
			break;
		case "New Window":
			Actions.performNewWindow(window);
			break;
		case "Open":
			Actions.performOpen(window);
			break;
		case "Save":
			Actions.performSave(window);
			break;
		case "Save As":
			Actions.performSaveAs(window);
			break;
		case "Print":
			Actions.performPrint(window);
			break;
		case "Exit":
			Actions.performExit(window);
			break;
		
		//Edit menu
		case "Cut":
			Actions.performCut(window);
			break;
		case "Copy":
			Actions.performCopy(window);
			break;
		case "Paste":
			Actions.performPaste(window);
			break;
		case "Select All":
			Actions.performSelectAll(window);
			break;
		case "Delete":
			Actions.performDelete(window);
			break;
		case "Find":
			Actions.performFind(window);
			break;
		case"Time and Date":
			window.getTextArea().addTimeAndDate();
			break;
		
		//Help menu
		case "About":
			Dialogs.about(window);
			break;
		}
	}
}
