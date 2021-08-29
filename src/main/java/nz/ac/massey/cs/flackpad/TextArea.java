package nz.ac.massey.cs.flackpad;

import java.awt.Font;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.text.BadLocationException;

import org.fife.rsta.ac.LanguageSupportFactory;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

@SuppressWarnings("serial")
class TextArea extends RSyntaxTextArea {

	private float fontSize;
	private int zoomPercentage;

	TextArea(Window window) {
		// Call RSyntaxTextArea constructor
		super();
		// Set variables
		fontSize = getFont().getSize();
		zoomPercentage = 100;
		// Set border
		setBorder(BorderFactory.createCompoundBorder(this.getBorder(), BorderFactory.createEmptyBorder(3, 5, 0, 5)));

		// Add listeners
		final TextAreaListener listener = new TextAreaListener(window);
		getDocument().addDocumentListener(listener);
		addCaretListener(listener);

		// Add language support
		LanguageSupportFactory.get().register(this);
	}

	void setTheme(Config config) {
		final MainTheme theme = config.getTheme();

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
	void addTimeAndDate() throws BadLocationException {
		// Set time and date format
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
		// Add time and date to text area
		getDocument().insertString(0, formatter.format(LocalDateTime.now()) + "\n", null);
	}

	void setFontWithZoom(Font font) {
		fontSize = font.getSize();
		setFont(font);
		zoom();
	}

	// Zoom methods work by changing font size
	void zoomIn() {
		zoomPercentage += 10;
		zoom();
	}

	void zoomOut() {
		zoomPercentage -= 10;
		zoom();
	}

	void resetZoom() {
		zoomPercentage = 100;
		zoom();
	}

	int getZoomPercentage() {
		return zoomPercentage;
	}

	private void zoom() {
		final float newSize = fontSize * zoomPercentage / 100;
		setFont(getFont().deriveFont(getFont().getStyle(), newSize > 1 ? newSize : 1));
	}
}
