package nz.ac.massey.cs.flackpad;

import java.io.*;
import javax.swing.*;

import org.apache.tika.Tika;

final class FileIO {

	final static int SAVED = 0, NOT_SAVED = 1;
	private final static int LOADED = 0, NOT_LOADED = 1, WRONG_TYPE = 2;

	private FileIO() {
		throw new UnsupportedOperationException();
	}

	static String getFileMIME(File file) {
		Tika tika = new Tika();
		String fileMIME = null;
		try {
			fileMIME = tika.detect(file);
		} catch (IOException e) {
			return fileMIME;
		}
		return fileMIME;
	}

	static void open(Window window) {
		int saveChoice = window.isSaved() ? 1 : Dialogs.saveWarning(window);
		int saved = SAVED;

		if (saveChoice == JOptionPane.CANCEL_OPTION || saveChoice == JOptionPane.CLOSED_OPTION) return;
		if (saveChoice == JOptionPane.YES_OPTION) saved = save(window);

		if (saved == SAVED) {
			JFileChooser fileChooser = new JFileChooser();
			if (fileChooser.showOpenDialog(window) == JFileChooser.APPROVE_OPTION) {
				int loaded = loadFile(fileChooser.getSelectedFile().getAbsoluteFile(), window);
				if (loaded == LOADED || loaded == WRONG_TYPE) {
					window.getTextArea().setCaretPosition(0);
					window.setFile(fileChooser.getSelectedFile().getAbsoluteFile());
					window.setSaved(true);
				} else if (loaded == NOT_LOADED) {
					Dialogs.error("Something went wrong when loading that file", window);
				}
				if (loaded == WRONG_TYPE) {
					Dialogs.warning("The contents of this file may not be displayed properly", window);
				}
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

	private static int loadFile(File file, Window window) {
		String fileMIME = getFileMIME(file);
		
		if (fileMIME.startsWith("text")) return loadTextFile(file, window);
		
		if (fileMIME.startsWith("application")) {
			switch (fileMIME.substring(12)) {
			case "x-bat":
				return loadTextFile(file, window);
			case "xml":
				return loadTextFile(file, window);
			}
		}
		
		loadTextFile(file, window);
		return WRONG_TYPE;
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

	private static int loadTextFile(File file, Window window) {
		window.getTextArea().setText("");

		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			
			while (line != null) {
				window.getTextArea().append(line);
				if ((line = reader.readLine()) != null)
					window.getTextArea().append("\n");
			}
			
			reader.close();
		} catch (IOException e) {
			return NOT_LOADED;
		}
		
		return LOADED;
	}

}