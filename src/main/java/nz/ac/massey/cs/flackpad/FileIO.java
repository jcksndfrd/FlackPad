package nz.ac.massey.cs.flackpad;

import java.io.*;
import javax.swing.*;

final class FileIO {
	
	final static int SAVED = 0, NOT_SAVED = 1;
	
	private FileIO() {
		throw new UnsupportedOperationException();
	}

	static void open(Window window) {
		int saveChoice = window.isSaved() ? 1 : Dialogs.saveWarning(window);
		int saved = SAVED;

		if (saveChoice == JOptionPane.CANCEL_OPTION || saveChoice == JOptionPane.CLOSED_OPTION) return;
		if (saveChoice == JOptionPane.YES_OPTION) saved = save(window);

		if (saved == SAVED) {
			JFileChooser fileChooser = new JFileChooser();
			if (fileChooser.showOpenDialog(window) == JFileChooser.APPROVE_OPTION) {
				loadFile(fileChooser.getSelectedFile().getAbsoluteFile(), window);
				window.setFile(fileChooser.getSelectedFile().getAbsoluteFile());
				window.setSaved(true);
			}
		}
	}
	
	static int save(Window window) {
		if (window.getFile() != null) {
			saveFile(window.getFile(), window);
			window.setSaved(true);
			return SAVED;
		}
		return saveAs(window);
	}

	static int saveAs(Window window) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Save As");
		if (fileChooser.showSaveDialog(window) == JFileChooser.APPROVE_OPTION) {
			saveFile(fileChooser.getSelectedFile().getAbsoluteFile(), window);
			window.setFile(fileChooser.getSelectedFile().getAbsoluteFile());
			window.setSaved(true);
			return SAVED;
		}
		return NOT_SAVED;
	}

	private static void loadFile(File file, Window window) {
		window.getTextArea().setText("");
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			while (line != null) {
				window.getTextArea().append(line);
				if ((line = reader.readLine()) != null) window.getTextArea().append("\n");
			}
		} catch (IOException e) {
			Dialogs.error("Something went wrong when loading that file", window);
		}
	}
	
	private static void saveFile(File file, Window window) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
			writer.write(window.getTextArea().getText());
			writer.flush();
			writer.close();
		} catch (IOException e) {
			Dialogs.error("Something went wrong when saving that file", window);
		}
	}
	
}