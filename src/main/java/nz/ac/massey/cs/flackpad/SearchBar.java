package nz.ac.massey.cs.flackpad;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

public class SearchBar {
	private JMenuBar menu;
	private JTextArea textArea;
	private JButton findButton;
	private JButton exitFindButton;
	private JButton replaceButton;

	private int warningBorderWidth = 1;
	private String defaultFindText;
	private String defaultReplaceText;

	private JTextField findField;
	private JTextField replaceField;
	private Border padding;

	public SearchBar(JTextArea textArea, JMenuBar menu) {
		this.menu = menu;
		this.textArea = textArea;
		JMenuItem item = new JMenuItem();
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

		addListenersToSearchBar();
	}

	private void addReplaceBar() {
		defaultReplaceText = "Replace with...";
		replaceField = new JTextField("");
		replaceField.setVisible(false);
		replaceField.setBorder(BorderFactory.createLineBorder(Color.decode("#000099"), warningBorderWidth));
		replaceField.setForeground(Color.GRAY);
		replaceField.setText(defaultReplaceText);
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

		defaultFindText = "Find text...";
		findField = new JTextField("");
		findField.setVisible(false);
		findField.setBorder(BorderFactory.createLineBorder(Color.decode("#000099"), warningBorderWidth));
		findField.setBorder(BorderFactory.createCompoundBorder(findField.getBorder(), padding));

		findField.setForeground(Color.GRAY);
		findField.setText(defaultFindText);
		menu.add(findField);

		findButton = new JButton("Find");
		findButton.setVisible(false);
		menu.add(findButton);
	}

	void findText() {
		try {
			if (!checkFindWarnings()) {
				return;
			}

			String searchtext = findField.getText();
			String text = textArea.getText();

			int offset = text.indexOf(searchtext);
			int length = searchtext.length();

			// Remove all current highlights
			textArea.getHighlighter().removeAllHighlights();

			Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(
					textArea.getSelectionColor());

			// Get all occurrences
			while (offset != -1) {
				try {
					textArea.getHighlighter().addHighlight(offset, offset + length, painter);
					offset = text.indexOf(searchtext, offset + 1);
				} catch (Exception e1) {
        			DialogUtils.warning("Could not highlight search phrase", textArea);
				}
			}

		} catch (Exception err) {
			DialogUtils.warning("Could not find search phrase", textArea);
		}
	}

	private void replaceText() {
		// If there is no text in the find bar or replace bar, do not replace anything
		if (!checkReplaceWarnings() || !checkFindWarnings()) {
			return;
		}
		try {
			String searchtext = findField.getText();
			String replacementtext = replaceField.getText();
			String result = textArea.getText().replaceAll(searchtext, replacementtext);
			textArea.setText(result);
		} catch (Exception e) {
			DialogUtils.warning("Could not replace occurrences of text", textArea);
		}
	}

	private boolean checkFindWarnings() {
		if (findField.getText().equals(defaultFindText)) {
			// Make field border red
			findField.setBorder(BorderFactory.createLineBorder(Color.decode("#990000"), warningBorderWidth));
			findField.setBorder(BorderFactory.createCompoundBorder(findField.getBorder(), padding));

			return false;
		}
		findField.setBorder(BorderFactory.createLineBorder(Color.decode("#000099"), warningBorderWidth));
		findField.setBorder(BorderFactory.createCompoundBorder(findField.getBorder(), padding));
		return true;
	}

	private boolean checkReplaceWarnings() {
		if (replaceField.getText().equals(defaultReplaceText)) {
			// Make field border red
			replaceField.setBorder(BorderFactory.createLineBorder(Color.decode("#990000"), warningBorderWidth));
			replaceField.setBorder(BorderFactory.createCompoundBorder(replaceField.getBorder(), padding));
			return false;
		}
		replaceField.setBorder(BorderFactory.createLineBorder(Color.decode("#000099"), warningBorderWidth));
		replaceField.setBorder(BorderFactory.createCompoundBorder(replaceField.getBorder(), padding));
		return true;
	}

	private void addListenersToSearchBar() {
		exitFindButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				hideFindBar();
			}
		});

		// Set search listener
		findField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				findText();
			}
		});
		// Set focus listener
		findField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (findField.getText().equals(defaultFindText)) {
					findField.setText("");
					findField.setForeground(Color.BLACK);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (findField.getText().isEmpty()) {
					findField.setForeground(Color.GRAY);
					findField.setText(defaultFindText);
				}
			}
		});
		findButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				findText();
			}
		});

		// Set replace listeners
		replaceField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				replaceText();
			}
		});
		// set focus listener
		replaceField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (replaceField.getText().equals(defaultReplaceText)) {
					replaceField.setText("");
					replaceField.setForeground(Color.BLACK);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (replaceField.getText().isEmpty()) {
					replaceField.setForeground(Color.GRAY);
					replaceField.setText(defaultReplaceText);
				}
			}
		});
		replaceButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				replaceText();
			}
		});
	}

	void hideFindBar() {
		try {
			findField.setVisible(false);
			findButton.setVisible(false);
			exitFindButton.setVisible(false);
			replaceButton.setVisible(false);
			replaceField.setVisible(false);
			textArea.getHighlighter().removeAllHighlights();
		} catch (Exception e) {
			DialogUtils.warning("Could not hide search bar", textArea);
		}

	}

	void showFindBar() {
		try {
			findField.setVisible(true);
			findButton.setVisible(true);
			exitFindButton.setVisible(true);
			findField.requestFocus();
			replaceButton.setVisible(true);
			replaceField.setVisible(true);
		} catch (Exception e) {
			DialogUtils.warning("Could not show search bar", textArea);
		}
	}

	JTextField getFindField() {
		return findField;
	}

	JButton getFindClose() {
		return exitFindButton;
	}
}
