package nz.ac.massey.cs.flackpad;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
class SaveAsChooser extends JFileChooser {

	public SaveAsChooser() {
		super();
	}

	public SaveAsChooser(String currentDirectory) {
		super(currentDirectory);
	}

	public SaveAsChooser(File currentDirectory) {
		super(currentDirectory);
	}

	@Override
	public void approveSelection() {
		final File file = getSelectedFile();

		if (file.exists()) {
			if (DialogUtils.overwriteWarning(file.getName(), this) == JOptionPane.YES_OPTION) {
				super.approveSelection();
			}
		} else {
			super.approveSelection();
		}
	}
}
