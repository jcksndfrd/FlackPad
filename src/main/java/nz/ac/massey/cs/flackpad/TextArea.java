package nz.ac.massey.cs.flackpad;

import java.awt.Font;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

@SuppressWarnings("serial")
class TextArea extends JTextArea {
	
	private Window window;
	private int fontSize;
	private int fontPercentage;

	TextArea(Window window) {
		//Call JTextArea constructor
		super();
		//Set variables
		this.window = window;
		this.fontSize = getFont().getSize();
		this.fontPercentage = 100;
		//set border and add document listener
		this.setBorder(BorderFactory.createCompoundBorder(this.getBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		this.getDocument().addDocumentListener(new DocListener(window));	      
	}
	
	//Adds time and date to the top of the text area
	void addTimeAndDate() {
		//Set time and date format
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
		try {
			//Add time and date to text area
			this.getDocument().insertString(0, formatter.format(LocalDateTime.now()) + "\n", null);
		} catch (BadLocationException e) {
			Dialogs.error("Something went wrong when getting the time and date", window);
		}
	}
	
	void setFontWithZoom(Font font) {
		fontSize = font.getSize();
		setFont(font);
		zoom();
	}
	
	//Zoom methods work by changing font size
	void zoomIn() {
		if (fontPercentage < 1000) {
			fontPercentage += 10;
			zoom();
		}
	}
	
	void zoomOut() {
		if (fontPercentage > 10) {
			fontPercentage -= 10;
			zoom();
		}
	}

	public void resetZoom() {
		fontPercentage = 100;
		zoom();
	}
	
	private void zoom() {
		Font newFont = new Font(getFont().getFamily(), getFont().getStyle(), Math.round(fontSize * fontPercentage / 100));
		setFont(newFont);
		window.getLineScrollTextArea().setFont(newFont);
	}
}
