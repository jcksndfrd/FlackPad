package nz.ac.massey.cs.flackpad;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;

class TextArea extends JTextArea {

	TextArea(Window window) {
		super();
		this.setBorder(BorderFactory.createCompoundBorder(this.getBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		this.getDocument().addDocumentListener(new DocListener(window));
	}
}
