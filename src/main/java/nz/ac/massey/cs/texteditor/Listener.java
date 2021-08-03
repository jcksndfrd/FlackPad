package nz.ac.massey.cs.texteditor;

import java.awt.event.*;

public class Listener implements ActionListener {

	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getActionCommand());
		switch (e.getActionCommand()) {
			case "New":
				// 1. Check if the current file is saved
				// 		Saved 	--> Create a new file
				//		Unsaved 	--> Force user to save file 
				
				// Load a new file into the current window
				
			case "New Window":
				// Load a new text editor instance
				new TextEditor();
				break;
		}
	}

}
