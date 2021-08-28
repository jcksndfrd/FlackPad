package nz.ac.massey.cs.flackpad;

import javax.swing.JMenuBar;
import javax.swing.JTextArea;
import javax.swing.text.Highlighter.Highlight;

import org.junit.jupiter.api.*;

public class TestSearchBar {
	private final JTextArea textArea = new JTextArea();
	private final SearchBar searchBar = new SearchBar(textArea, new JMenuBar());
	
	@Test
	public void testSearch() {
		String testText = "oijsefoisjftestoisefjisjfe/niushefsheftest";
		String searchText = "test";
		
		// Set text area text
		textArea.setText(testText);
		// Set search phrase
		searchBar.getFindField().setText(searchText);
		// Perform search
		searchBar.findText();
		// Check highlighted text
		Highlight[] highlights = textArea.getHighlighter().getHighlights();
		for (Highlight highlight : highlights) {
			Assertions.assertEquals(testText.substring(highlight.getStartOffset(), highlight.getEndOffset()), searchText);
		}
	}
}
