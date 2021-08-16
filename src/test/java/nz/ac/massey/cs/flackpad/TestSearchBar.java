package nz.ac.massey.cs.flackpad;

import javax.swing.text.Highlighter.Highlight;

import org.junit.jupiter.api.*;

public class TestSearchBar {
	private final Window window = new Window(true);
	
	@Test
	public void testSearch() {
		String testText = "oijsefoisjftestoisefjisjfe/niushefsheftest";
		String searchText = "test";
		
		// Set text area text
		window.setText(testText);
		// Set search phrase
		window.getFindField().setText(searchText);
		// Perform search
		window.getSearchBar().findText();
		// Check highlighted text
		Highlight[] highlights = window.getTextArea().getHighlighter().getHighlights();
		for (Highlight highlight : highlights) {
			Assertions.assertEquals(testText.substring(highlight.getStartOffset(), highlight.getEndOffset()), searchText);
		}
	}
}
