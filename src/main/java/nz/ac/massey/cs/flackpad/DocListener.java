package nz.ac.massey.cs.flackpad;

import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

class DocListener implements DocumentListener, CaretListener {
	
	private Window window;

	DocListener(Window window) {
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
//		window.updateInformationBar();
		window.updateCCDEnable();
	}
}
