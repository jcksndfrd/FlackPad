package nz.ac.massey.cs.flackpad;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class KeyBinder extends AbstractAction {
	private final TextArea areaInstance;
	private final Window windowInstance;

	KeyBinder(Window windowInstance) {
		super();
		this.areaInstance = windowInstance.getTextArea();
		this.windowInstance = windowInstance;
		addKeyBindings();
	}

	private void addKeyBindings() {
		// Bind to main textarea
		bindToMainArea(areaInstance, KeyStroke.getKeyStroke("ESCAPE"), "escape_find", this);
		// Bind to search field
		final JTextField textfield = windowInstance.getFindField();
		textfield.getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), "escape_find");
		textfield.getActionMap().put("escape_find", this);
	}

	private void bindToMainArea(TextArea area, KeyStroke key, String msg, Action act) {
		area.getInputMap().put(key, msg);
		area.getActionMap().put(msg, act);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		TextActionUtils.performEscapeFind(windowInstance);
	}
}
