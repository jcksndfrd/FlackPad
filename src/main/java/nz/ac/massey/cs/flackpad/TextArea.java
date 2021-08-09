package nz.ac.massey.cs.flackpad;

import java.awt.Font;
import java.awt.Point;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import java.awt.Color;


@SuppressWarnings("serial")
class TextArea extends RSyntaxTextArea {
	
	private Window window;
	private Config config;
	
	private int fontSize;
	private int fontPercentage;
	
	private CaretListener mainCaretListener;

	TextArea(Window window, Config config) {
		//Call RSyntaxTextArea constructor
		super();
		//Set variables
		this.window = window;
		this.config = config;
		
		this.fontSize = getFont().getSize();
		this.fontPercentage = 100;
		//set border and add document listener
		this.setBorder(BorderFactory.createCompoundBorder(this.getBorder(), BorderFactory.createEmptyBorder(5, 5, 0, 5)));
		this.getDocument().addDocumentListener(new DocListener(window));	
		this.addCaretListener(new CaretListener() {
	        public void caretUpdate(CaretEvent e) {
	            updateInformationBar();
	        }
	    });	
		setTheme();
	}
	
	TextArea(Window window, Config config, String startVal) {
		//Call RSyntaxTextArea constructor
		super();
		//Set variables
		this.window = window;
		this.config = config;
		
		this.fontSize = getFont().getSize();
		this.fontPercentage = 100;
		//set border and add document listener
		this.setBorder(BorderFactory.createCompoundBorder(this.getBorder(), BorderFactory.createEmptyBorder(5, 5, 0, 5)));
		setFont(config.getFont());
		this.setText(startVal);
		this.setHighlighter(null);
		setCurrentLineHighlightColor(new Color(12, 12, 12, 0)); // Hide highlight
	}
	
	void setTheme() {
		setCaretColor(Color.decode("#eeeeee")); // caret color
		setSelectionColor(Color.decode("#770BD8")); // selection color
		setBackground(Color.decode("#333333"));
		setForeground(Color.decode("#aaaaaa"));
		setCurrentLineHighlightColor(Color.decode("#444444")); // line highlight color
		
		setSyntaxEditingStyle("text/plain");
		setCodeFoldingEnabled(true);
		setFont(config.getFont());
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
		window.getLines().setFont(newFont);
	}
	
	private void updateInformationBar() {
		TextArea area = window.getTextArea();
		
		String text = "";
        try
        {
        	text = Integer.toString(area.getText().length()) + " | Char";
        	
            // Update text
    		window.setInformationBar(text);
    	}
        catch (NullPointerException exc)
        {
            exc.printStackTrace();
        }
	}
}
