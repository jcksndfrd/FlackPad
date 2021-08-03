package nz.ac.massey.cs.texteditor;

import java.awt.event.*;

public class Listener implements ActionListener {
	
	private TextEditor frame;
	
	public Listener(TextEditor frame) {
		this.frame = frame;
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		
		if (action == "Open") {
			FileIO.open(frame);
		}
		if (action == "Save") {
			FileIO.save(frame);
		}
		if (action == "Save As") {
			FileIO.saveAs(frame);
		}
	}
}
