package nz.ac.massey.cs.flackpad;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JTextField;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

public class SearchBar {
	private JMenuBar menu;
	private Window window;
	private JButton exitFindButton;
	private JTextField findField;
	private ActionListener menuListener;
	

	public SearchBar(Window window, JMenuBar menu, ActionListener menuListener) {
		this.menu = menu;
		this.window = window;
		this.menuListener = menuListener;
		this.addFindBar();
	}

	private void addFindBar() {
		exitFindButton = new JButton("X");
		exitFindButton.setVisible(false);
		menu.add(exitFindButton);

		findField = new JTextField("");
		findField.setVisible(false);
		findField.addActionListener(menuListener);
		menu.add(findField);

		addListenersToFindBar();
	}

	private void addListenersToFindBar() {
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
				try {
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
		});
	}

	void hideFindBar() {
		try {
			findField.setVisible(false);
			exitFindButton.setVisible(false);
			TextArea textarea = window.getTextArea();
			textarea.getHighlighter().removeAllHighlights();
		} catch (Exception e) {
			Dialogs.error("Could not hide search bar", window.getFrame());
		}

	}

	void showFindBar() {
		try {
			findField.setVisible(true);
			exitFindButton.setVisible(true);
			findField.requestFocus();
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
