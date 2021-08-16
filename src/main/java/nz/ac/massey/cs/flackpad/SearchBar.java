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
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

public class SearchBar {
	private JMenuBar menu;
	private Window window;
	private JButton findButton;
	private JButton exitFindButton;
	private JButton replaceButton;

	private int warningBorderWidth = 1;
	private String defaultFindText;
	private String defaultReplaceText;

	private JTextField findField;
	private JTextField replaceField;
	private Border padding;
	private ActionListener menuListener;

	public SearchBar(Window window, JMenuBar menu, ActionListener menuListener) {
		this.menu = menu;
		this.window = window;
		this.menuListener = menuListener;
		JMenuItem item = new JMenuItem();
		item.setMargin(new Insets(2, 5, 2, 5));
		item.setBackground(Color.white);
		padding = BorderFactory.createEmptyBorder(1, 5, 1, 5);
		menu.add(item); // Left margin
		this.addFindBar();
		this.addReplaceBar();
		menu.add(item); // Right margin

		addListenersToSearchBar();
	}

	private void addReplaceBar() {
		defaultReplaceText = "Replace with...";
		replaceField = new JTextField("");
		replaceField.setVisible(false);
		replaceField.addActionListener(menuListener);
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
		findField.addActionListener(menuListener);
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
			TextArea textarea = window.getTextArea();

			String searchtext = findField.getText();
			String text = textarea.getText();

			int offset = text.indexOf(searchtext);
			int length = searchtext.length();

			// Remove all current highlights
			textarea.getHighlighter().removeAllHighlights();

			Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(
					window.getTextArea().getSelectionColor());

			// Get all occurrences
			while (offset != -1) {
				try {
					textarea.getHighlighter().addHighlight(offset, offset + length, painter);
					offset = text.indexOf(searchtext, offset + 1);
				} catch (Exception e1) {
					Dialogs.error("Could not highlight search phrase", window.getFrame());
				}
			}

		} catch (Exception err) {
			Dialogs.error("Issue with find / replace listeners", window.getFrame());
		}
	}

	private void replaceText() {
		// If there is no text in the find bar or replace bar, do not replace anything
		if (!checkReplaceWarnings() || !checkFindWarnings()) {
			return;
		}
		try {
			TextArea textarea = window.getTextArea();
			String searchtext = findField.getText();
			String replacementtext = replaceField.getText();
			String result = textarea.getText().replaceAll(searchtext, replacementtext);
			textarea.setText(result);
		} catch (Exception e) {
			System.out.println("Could not replace occurences of text");
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
			TextArea textarea = window.getTextArea();
			textarea.getHighlighter().removeAllHighlights();
		} catch (Exception e) {
			Dialogs.error("Could not hide search bar", window.getFrame());
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
			Dialogs.error("Could not show search bar", window.getFrame());
		}
	}

	JTextField getFindField() {
		return findField;
	}

	JButton getFindClose() {
		return exitFindButton;
	}
}
