package nz.ac.massey.cs.flackpad;

import java.awt.Color;
class Theme {
	private Color textBackground;
	private Color gutterBackground;

	public Theme(String theme) {
		// Set theme colours
		switch (theme) {
		case "dark":
			setDarkTheme();
			break;
		case "light":
			setLightTheme();
			break;
		default:
			// Incorrect theme parameter
			throw new IllegalArgumentException("Unknown theme " + theme + ". Please choose from \"dark\" or \"light\"");
		}
	}

	private void setLightTheme() {
		textBackground = Color.WHITE;
		gutterBackground = Color.decode("#BBBBBB");
	}

	private void setDarkTheme() {
		textBackground = Color.decode("#333333");
		gutterBackground = Color.decode("#383838");
	}

	Color getTextBackground() {
		return textBackground;
	}

	Color getGutterBackground() {
		return gutterBackground;
	}

}
