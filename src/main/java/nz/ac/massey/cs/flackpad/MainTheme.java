package nz.ac.massey.cs.flackpad;

import java.io.IOException;

import org.fife.ui.rsyntaxtextarea.Theme;

final class MainTheme {
	private String themeName;

	// Syntax theme
	private Theme syntaxTheme;

	public MainTheme(String themeName) {
		try {
			setTheme(themeName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setTheme(String themeName) throws IOException {
		syntaxTheme = Theme.load(getClass().getResourceAsStream("/themes/" + themeName + ".xml"));		
		this.themeName = themeName;
	}

	public String getThemeName() {
		return themeName;
	}
	
	Theme getSyntaxTheme() {
		return syntaxTheme;
	}

}
