package nz.ac.massey.cs.flackpad;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class MenuListener implements ActionListener {

	private final Window window;

	MenuListener(Window window) {
		this.window = window;
	}

	@Override
	public void actionPerformed(ActionEvent event) {

		switch (event.getActionCommand()) {
		//File menu
		case "New":
			window.newDoc();
			break;
		case "New Window":
			new Window();
			break;
		case "Open":
			window.getFileIO().open();
			break;
		case "Save":
			window.getFileIO().save();
			break;
		case "Save As...":
			window.getFileIO().saveAs();
			break;
		case "Print":
			window.print();
			break;
		case "Export as...":
			window.export();
			break;
		case "Exit":
			window.exit();
			break;
			
			//Edit menu
			case "Undo":
				window.undo();
				break;
			case "Redo":
				window.redo();
				break;
			case "Cut":
				TextActionUtils.performCut(window);
				break;
			case "Copy":
				TextActionUtils.performCopy(window);
				break;
			case "Paste":
				TextActionUtils.performPaste(window);
				break;
			case "Select All":
				TextActionUtils.performSelectAll(window);
				break;
			case "Delete":
				TextActionUtils.performDelete(window);
				break;
			case "Find":
				TextActionUtils.performFind(window);
				break;
			case "Time and Date":
				window.addTimeAndDate();
				break;
				
			//View menu
			case "Zoom In":
				window.zoomIn();
				break;
			case "Zoom Out":
				window.zoomOut();
				break;
			case "Reset Zoom":
				window.resetZoom();
				break;
			case "Line Numbers":
				window.gutterToggle();
				break;
			case "Toggle Theme":
				window.toggleTheme();
				break;
			case "Font and Theme":
				window.openThemeDialog();
				break;
			
			//Help menu
			case "About":
				AboutDialog.show(window.getFrame(), window.getIcon());
				break;
		}
	}
}
