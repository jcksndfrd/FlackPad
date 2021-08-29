package nz.ac.massey.cs.flackpad;

import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

class TextAreaListener implements DocumentListener, CaretListener {
	
	private final Window window;

	TextAreaListener(Window window) {
		super();
		this.window = window;
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		window.setSaved(false);
		window.updateInformationBar();
		window.updateUndoRedoEnable();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		window.setSaved(false);
		window.updateInformationBar();
		window.updateUndoRedoEnable();
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		window.setSaved(false);
		window.updateInformationBar();
		window.updateUndoRedoEnable();
	}

	@Override
	public void caretUpdate(CaretEvent e) {
		window.updateCCDEnable();
	}
	
}
