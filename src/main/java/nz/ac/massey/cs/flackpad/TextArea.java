package nz.ac.massey.cs.flackpad;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

@SuppressWarnings("serial")
class TextArea extends JTextArea {
	
	Window window;

	TextArea(Window window) {
		super();
		this.window = window;
		this.setBorder(BorderFactory.createCompoundBorder(this.getBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		this.getDocument().addDocumentListener(new DocListener(window));
	}
	
	void addTimeAndDate() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
		try {
			this.getDocument().insertString(0, formatter.format(LocalDateTime.now()) + "\n", null);
		} catch (BadLocationException e) {
			Dialogs.error("Something went wrong when getting the time and date", window);
		}
	}
	
}
