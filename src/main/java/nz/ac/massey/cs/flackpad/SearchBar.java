package nz.ac.massey.cs.flackpad;

import java.awt.Color;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

public class SearchBar {
	final private JMenuBar menu;
	final private JTextArea textArea;
	private JButton findButton;
	private JButton exitFindButton;
	private JButton replaceButton;
	final static private int WARNING_WIDTH = 1;
	private String defaultFText;
	private String defaultRText;
	private JTextField findField;
	private JTextField replaceField;
	final private Border padding;
	final private SearchBarListener searchBarListener;
	final static private Color BLUE = Color.decode("#000099");

	public SearchBar(JTextArea textArea, JMenuBar menu) {
		this.menu = menu;
		this.textArea = textArea;
		final JMenuItem item = new JMenuItem();
		item.removeMouseListener(item.getMouseListeners()[0]);
		item.setFocusable(false);
		item.setMargin(new Insets(2, 5, 2, 5));
		item.setBackground(Color.white);
		padding = BorderFactory.createEmptyBorder(1, 5, 1, 5);
		menu.add(item); // Left margin
		item.setEnabled(false);
		this.addFindBar();
		this.addReplaceBar();
		menu.add(item); // Right margin

		
		searchBarListener = new SearchBarListener(this);
		addListenersToSearchBar();
	}

	private void addReplaceBar() {
		defaultRText = "Replace with...";
		replaceField = new JTextField("");
		replaceField.setVisible(false);
		replaceField.setBorder(BorderFactory.createLineBorder(BLUE, WARNING_WIDTH));
		replaceField.setForeground(Color.GRAY);
		replaceField.setText(defaultRText);
		replaceField.setBorder(BorderFactory.createCompoundBorder(replaceField.getBorder(), padding));

		menu.add(replaceField);

		replaceButton = new JButton("Replace");
		replaceButton.setVisible(false);
		menu.add(replaceButton);
	}

	private void addFindBar() {

		exitFindButton = new JButton("X");
		exitFindButton.setVisible(false);
		menu.add(exitFindButton);

		defaultFText = "Find text...";
		findField = new JTextField("");
		findField.setVisible(false);
		findField.setBorder(BorderFactory.createLineBorder(BLUE, WARNING_WIDTH));
		findField.setBorder(BorderFactory.createCompoundBorder(findField.getBorder(), padding));

		findField.setForeground(Color.GRAY);
		findField.setText(defaultFText);
		menu.add(findField);

		findButton = new JButton("Find");
		findButton.setVisible(false);
		menu.add(findButton);
	}

	void findText() {
		if (checkFindWarnings()) {
			final String searchtext = findField.getText();
			final String text = textArea.getText();

			int offset = text.indexOf(searchtext);
			final int length = searchtext.length();

			// Remove all current highlights
			textArea.getHighlighter().removeAllHighlights();

			final Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(
					textArea.getSelectionColor());

			// Get all occurrences
			while (offset != -1) {
				try {
					textArea.getHighlighter().addHighlight(offset, offset + length, painter);
					offset = text.indexOf(searchtext, offset + 1);
				} catch (BadLocationException error) {
        			DialogUtils.warning("Could not highlight search phrase", textArea);
				}
			}
		}
	}

	void replaceText() {
		// If there is no text in the find bar or replace bar, do not replace anything
		if (checkReplaceWarnings() && checkFindWarnings()) {	
			final String searchtext = findField.getText();
			final String replacementtext = replaceField.getText();
			final String result = textArea.getText().replaceAll(searchtext, replacementtext);
			textArea.setText(result);
		}
	}

	private boolean checkFindWarnings() {
		boolean checkWarning = false;
		if (findField.getText().equals(defaultFText)) {
			// Make field border red
			findField.setBorder(BorderFactory.createLineBorder(Color.decode("#990000"), WARNING_WIDTH));
			findField.setBorder(BorderFactory.createCompoundBorder(findField.getBorder(), padding));
		} else
		{
			findField.setBorder(BorderFactory.createLineBorder(BLUE, WARNING_WIDTH));
			findField.setBorder(BorderFactory.createCompoundBorder(findField.getBorder(), padding));
			checkWarning = true;
		}
		return checkWarning;
	}

	private boolean checkReplaceWarnings() {
		boolean checkWarning = false;
		if (replaceField.getText().equals(defaultRText)) {
			// Make field border red
			replaceField.setBorder(BorderFactory.createLineBorder(Color.decode("#990000"), WARNING_WIDTH));
			replaceField.setBorder(BorderFactory.createCompoundBorder(replaceField.getBorder(), padding));
		}
		else {
			replaceField.setBorder(BorderFactory.createLineBorder(BLUE, WARNING_WIDTH));
			replaceField.setBorder(BorderFactory.createCompoundBorder(replaceField.getBorder(), padding));
			checkWarning = true;
		}
		return checkWarning;
	}

	private void addListenersToSearchBar() {
		// Set close listener
		exitFindButton.addActionListener(searchBarListener);
		// Set search listener
		findField.addActionListener(searchBarListener);
		// Set find field listener
		findField.addFocusListener(searchBarListener);
		// Set find button listener
		findButton.addActionListener(searchBarListener);
		// Set replace listener
		replaceField.addActionListener(searchBarListener);
		// Set relace field listener
		replaceField.addFocusListener(searchBarListener);
		// Set replace button listener
		replaceButton.addActionListener(searchBarListener);
	}
	void setFindField() {
		if (findField.getText().equals(defaultFText)) {
			findField.setText("");
			findField.setForeground(Color.BLACK);
		}
	}
	void resetFindField() {
		if (findField.getText().isEmpty()) {
			findField.setText(defaultFText);
			findField.setForeground(Color.GRAY);
		}
	}
	void setReplaceField() {
		if (replaceField.getText().equals(defaultRText)) {
			replaceField.setText("");
			replaceField.setForeground(Color.BLACK);
		}
	}
	void resetReplaceField() {
		if (replaceField.getText().isEmpty()) {
			replaceField.setForeground(Color.GRAY);
			replaceField.setText(defaultRText);
		}
	}

	void hideFindBar() {
		findField.setVisible(false);
		findButton.setVisible(false);
		exitFindButton.setVisible(false);
		replaceButton.setVisible(false);
		replaceField.setVisible(false);
		textArea.getHighlighter().removeAllHighlights();
	}

	void showFindBar() {
		findField.setVisible(true);
		findButton.setVisible(true);
		exitFindButton.setVisible(true);
		findField.requestFocus();
		replaceButton.setVisible(true);
		replaceField.setVisible(true);
	}

	JTextField getFindField() {
		return findField;
	}
	JTextField getReplaceField() {
		return replaceField;
	}
	JButton getFindButton() {
		return findButton;
	}
	JButton getReplaceButton() {
		return replaceButton;
	}
	JButton getFindClose() {
		return exitFindButton;
	}

}
