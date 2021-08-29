package nz.ac.massey.cs.flackpad;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

public class KeyBinder {
	private final TextArea areaInstance;
	private final Window windowInstance;

	KeyBinder(Window windowInstance) {
		this.areaInstance = windowInstance.getTextArea();
		this.windowInstance = windowInstance;
		addKeyBindings();
	}

	private void addKeyBindings() {
		// Escape key binding
		@SuppressWarnings("serial")
		final Action escapeFind = new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent eevent) {
				TextActionUtils.performEscapeFind(windowInstance);
			}

		};

		// Bind to main textarea
		bindToMainArea(areaInstance, KeyStroke.getKeyStroke("ESCAPE"), "escape_find", escapeFind);
		// Bind to search field
		final JTextField textfield = windowInstance.getFindField();
		textfield.getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), "escape_find");
		textfield.getActionMap().put("escape_find", escapeFind);
	}

	private void bindToMainArea(TextArea area, KeyStroke key, String msg, Action act) {
		area.getInputMap().put(key, msg);
		area.getActionMap().put(msg, act);
	}
}
