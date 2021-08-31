package nz.ac.massey.cs.flackpad;

import java.awt.Color;
import java.io.IOException;

import org.fife.ui.rsyntaxtextarea.Theme;

final class MainTheme {
	private String themeName;

	// Syntax theme
	private Theme syntaxTheme;
	private Color scrollbar;
	
	void setTheme(String themeName) throws IOException {
		syntaxTheme = Theme.load(getClass().getResourceAsStream("/themes/" + themeName + ".xml"));
		scrollbar = "dark".equals(themeName) ? Color.decode("#202020") : Color.decode("#aaaaaa");
		this.themeName = themeName;
	}

	String getThemeName() {
		return themeName;
	}
	
	Theme getSyntaxTheme() {
		return syntaxTheme;
	}
	
	Color getScrollbarColor() {
		return scrollbar;
	}

}
