package nz.ac.massey.cs.flackpad;

import javax.swing.event.*;

class DocListener implements DocumentListener {
	
	private Window window;

	DocListener(Window window) {
		super();
		this.window = window;
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		window.setSaved(false);
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		window.setSaved(false);
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		window.setSaved(false);
	}

}
