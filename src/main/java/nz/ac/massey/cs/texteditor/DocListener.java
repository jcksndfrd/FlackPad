package nz.ac.massey.cs.texteditor;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class DocListener implements DocumentListener {
	
	private TextEditor frame;

	public DocListener(TextEditor frame) {
		super();
		this.frame = frame;
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		frame.setSaved(false);
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		frame.setSaved(false);
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		frame.setSaved(false);
	}

}
