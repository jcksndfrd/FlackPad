package nz.ac.massey.cs.flackpad;

import java.awt.Color;
class Theme {
	static Color textBackground;
	static Color gutterBackground;
	static Color gutterLineNumber;
	static Color gutterBorder;
	static Color textForeground;
	static Color currentLineHightlightBackground;
	static Color selectionHighlight;
	static Color caretForeground;

	public Theme(String theme) {
		// Set theme colours
		switch (theme) {
		case "dark":
			setLightTheme();
			break;
		case "light":
			setLightTheme();
			break;
		default:
			// Incorrect theme parameter
			setDarkTheme();
			throw new IllegalArgumentException("Unknown theme " + theme + ". Please choose from \"dark\" or \"light\"");
		}
	}

	private void setLightTheme() {
		textBackground = Color.white;
		textForeground = Color.decode("#383838");
		gutterBackground = Color.white;
		gutterBorder =  Color.decode("#383838");
		gutterLineNumber = Color.decode("#990000");
		currentLineHightlightBackground = Color.decode("#eeeeee");
		selectionHighlight = Color.decode("#00ff00");
		caretForeground = Color.decode("#383838");
	}

	private void setDarkTheme() {
		textBackground = Color.decode("#333333");
		textForeground = Color.decode("#aaaaaa");
		gutterBackground = Color.decode("#383838");
		gutterBorder = Color.decode("#770BD8");	
		gutterLineNumber = Color.decode("#aaaaaa");
		currentLineHightlightBackground = Color.decode("#444444");
		selectionHighlight = Color.decode("#770BD8");
		caretForeground = Color.decode("#eeeeee");
	}
}
