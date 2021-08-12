package nz.ac.massey.cs.flackpad;

import java.awt.Font;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

@SuppressWarnings("serial")
class TextArea extends RSyntaxTextArea {
	
	private Window window;
	
	private int fontSize;
	private int fontPercentage;
	
	TextArea(Window window) {
		// Call RSyntaxTextArea constructor
		super();
		// Set variables
		this.window = window;

		this.fontSize = getFont().getSize();
		this.fontPercentage = 100;
		// Set border and add document listener
		this.setBorder(BorderFactory.createCompoundBorder(this.getBorder(), BorderFactory.createEmptyBorder(3, 5, 0, 5)));
		this.getDocument().addDocumentListener(new DocListener(window));	
		this.addCaretListener(new CaretListener() {
	        public void caretUpdate(CaretEvent e) {
	            updateInformationBar();
	        }
	    });	
	}
	
	void setTheme(Config config) {
		// Set font
		setFontWithZoom(config.getFont());
		
		// Set colours		
		setBackground(MainTheme.textBackground);
		// Add these to theme and config
		setCaretColor(MainTheme.caretForeground); // caret color
		setSelectionColor(MainTheme.selectionHighlight); // selection color
		setForeground(MainTheme.textForeground);
		setCurrentLineHighlightColor(MainTheme.currentLineHightlightBackground); // line highlight color
		setCodeFoldingEnabled(true);
	}
	
	//Adds time and date to the top of the text area
	void addTimeAndDate() {
		//Set time and date format
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
		try {
			//Add time and date to text area
			this.getDocument().insertString(0, formatter.format(LocalDateTime.now()) + "\n", null);
		} catch (BadLocationException e) {
			Dialogs.error("Something went wrong when getting the time and date", window.getFrame());
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
		int roundedZoomVal = Math.round(fontSize * fontPercentage / 100);
		Font newFont = new Font(getFont().getFamily(), getFont().getStyle(), roundedZoomVal);
		setFont(newFont);
		try {
			if (fontPercentage == 100) {
				// Hide percentage information
				window.setInformationBarZoomVisible(false);
			} else {
				window.setInformationBarZoomVisible(true);
				window.setInformationBarZoomText(Integer.toString(fontPercentage) + "%");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void updateInformationBar() {		
        try {
            // Update text
    		window.setInformationBar(Integer.toString(window.getText().length()) + " | Char");
    	} catch (NullPointerException e) {
    		window.setInformationBar("0");
        }
	}
}
