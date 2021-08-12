package nz.ac.massey.cs.flackpad;

import java.awt.Font;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.text.BadLocationException;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

@SuppressWarnings("serial")
class TextArea extends RSyntaxTextArea {

	private Window window;

	private int fontSize;
	private int zoomPercentage;

	TextArea(Window window) {
		// Call RSyntaxTextArea constructor
		super();
		// Set variables
		this.window = window;

		fontSize = getFont().getSize();
		zoomPercentage = 100;
		// Set border
		setBorder(BorderFactory.createCompoundBorder(this.getBorder(), BorderFactory.createEmptyBorder(3, 5, 0, 5)));

		// Add listeners
		DocListener listener = new DocListener(window);
		getDocument().addDocumentListener(listener);
//		addCaretListener(listener);
	}

	void setTheme(Config config) {
		MainTheme theme = config.getTheme();

		// Set syntax theme
		theme.getSyntaxTheme().apply(this);

		// Set colours
		setBackground(theme.getTextBackground());
		setCaretColor(theme.getCaretForeground()); // caret color
		setSelectionColor(theme.getSelectionHighlight()); // selection color
		setForeground(theme.getTextForeground());
		setCurrentLineHighlightColor(theme.getCurrentLineHighlightBackground()); // line highlight color
		setCodeFoldingEnabled(true);

		// Set font
		setFontWithZoom(config.getFont());
	}

	// Adds time and date to the top of the text area
	void addTimeAndDate() {
		// Set time and date format
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
		try {
			// Add time and date to text area
			getDocument().insertString(0, formatter.format(LocalDateTime.now()) + "\n", null);
		} catch (BadLocationException e) {
			Dialogs.error("Something went wrong when getting the time and date", window.getFrame());
		}
	}

	void setFontWithZoom(Font font) {
		fontSize = font.getSize();
		setFont(font);
		zoom();
	}

	// Zoom methods work by changing font size
	int zoomIn() {
		if (zoomPercentage < 1000) {
			zoomPercentage += 10;
			zoom();
		}
		return zoomPercentage;
	}

	int zoomOut() {
		if (zoomPercentage > 10) {
			zoomPercentage -= 10;
			zoom();
		}
		return zoomPercentage;
	}

	void resetZoom() {
		zoomPercentage = 100;
		zoom();
	}

	int getZoomPercentage() {
		return zoomPercentage;
	}

	private void zoom() {
		int roundedZoomVal = Math.round(fontSize * zoomPercentage / 100);
		Font newFont = new Font(getFont().getFamily(), getFont().getStyle(), roundedZoomVal);
		setFont(newFont);
	}
}
