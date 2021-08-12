package nz.ac.massey.cs.flackpad;

import java.awt.Color;
import java.io.IOException;

import org.fife.ui.rsyntaxtextarea.Theme;

class MainTheme {
	private String themeName;

	// Syntax theme
	private Theme syntaxTheme;

	// Colours
	private Color textBackground;
	private Color gutterBackground;
	private Color gutterLineNumber;
	private Color gutterBorder;
	private Color textForeground;
	private Color currentLineHighlightBackground;
	private Color selectionHighlight;
	private Color caretForeground;

	public MainTheme(String themeName) {
		try {
			setTheme(themeName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setTheme(String themeName) throws IOException {
		// Set theme colours
		switch (themeName) {
		case "dark":
			setDarkTheme();
			break;
		case "light":
			setLightTheme();
			break;
		default:
			// Incorrect theme parameter
			throw new IllegalArgumentException(
					"Unknown theme " + themeName + ". Please choose from \"dark\" or \"light\"");
		}
		
		this.themeName = themeName;
	}

	private void setLightTheme() throws IOException {
		// Syntax theme
		syntaxTheme = Theme.load(getClass().getResourceAsStream("/org/fife/ui/rsyntaxtextarea/themes/" + "default" + ".xml"));
		
		// Text area colours
		textBackground = Color.white;
		textForeground = Color.decode("#383838");
		currentLineHighlightBackground = Color.decode("#eeeeee");
		selectionHighlight = Color.decode("#00ff00");
		caretForeground = Color.decode("#383838");

		// Gutter colours
		gutterBackground = Color.white;
		gutterBorder = Color.decode("#383838");
		gutterLineNumber = Color.decode("#990000");
	}

	private void setDarkTheme() throws IOException {
		// Syntax theme
		syntaxTheme = Theme.load(getClass().getResourceAsStream("/org/fife/ui/rsyntaxtextarea/themes/" + "dark" + ".xml"));
		
		// Text area colours
		textBackground = Color.decode("#333333");
		textForeground = Color.decode("#aaaaaa");
		currentLineHighlightBackground = Color.decode("#444444");
		selectionHighlight = Color.decode("#770BD8");
		caretForeground = Color.decode("#eeeeee");

		// Gutter colours
		gutterBackground = Color.decode("#383838");
		gutterBorder = Color.decode("#770BD8");
		gutterLineNumber = Color.decode("#aaaaaa");
	}

	public String getThemeName() {
		return themeName;
	}
	
	Theme getSyntaxTheme() {
		return syntaxTheme;
	}
	
	Color getTextBackground() {
		return textBackground;
	}

	Color getGutterBackground() {
		return gutterBackground;
	}

	Color getGutterLineNumber() {
		return gutterLineNumber;
	}

	Color getGutterBorder() {
		return gutterBorder;
	}

	Color getTextForeground() {
		return textForeground;
	}

	Color getCurrentLineHighlightBackground() {
		return currentLineHighlightBackground;
	}

	Color getSelectionHighlight() {
		return selectionHighlight;
	}

	Color getCaretForeground() {
		return caretForeground;
	}

}
