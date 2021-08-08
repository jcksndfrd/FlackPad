package nz.ac.massey.cs.flackpad;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.apache.tika.Tika;

final class FileIO {

	final static int SAVED = 0, NOT_SAVED = 1;
	private final static int NOT_LOADED = -1, LOADED = 0, WRONG_TYPE = 1, IMPORTED = 2;
	
	private static FileMIME MIME = new FileMIME();

	private FileIO() {
		throw new UnsupportedOperationException();
	}

	static void open(Window window) {
		int saveChoice = window.isSaved() ? 1 : Dialogs.saveWarning(window.getFileName(), window);
		int saved = SAVED;

		if (saveChoice == JOptionPane.CANCEL_OPTION || saveChoice == JOptionPane.CLOSED_OPTION)
			return;
		if (saveChoice == JOptionPane.YES_OPTION)
			saved = save(window);

		if (saved == SAVED) {
			JFileChooser fileChooser = new JFileChooser();
			if (fileChooser.showOpenDialog(window) == JFileChooser.APPROVE_OPTION) {
				int loaded = loadFile(fileChooser.getSelectedFile().getAbsoluteFile(), window);

				if (loaded == LOADED || loaded == WRONG_TYPE || loaded == IMPORTED) {
					window.getTextArea().setCaretPosition(0);
				}

				if (loaded == LOADED || loaded == WRONG_TYPE) {
					window.setFile(fileChooser.getSelectedFile().getAbsoluteFile());
					window.setSaved(true);
				}

				if (loaded == WRONG_TYPE) {
					Dialogs.warning("The contents of this file may not be displayed properly", window);
				}

				if (loaded == NOT_LOADED) {
					Dialogs.error("Something went wrong when loading that file", window);
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
		String fileMIME = MIME.getFileMIME(file);

		try {
			if (fileMIME.startsWith("text")) {
				window.setText(TextFileLoader.loadFile(file));
				return LOADED;
			}

			if (fileMIME.startsWith("application")) {
				switch (fileMIME.substring(12)) {
				case "x-bat":
					window.setText(TextFileLoader.loadFile(file));
					return LOADED;
				case "xml":
					window.setText(TextFileLoader.loadFile(file));
					return LOADED;
				case "vnd.oasis.opendocument.text":
					window.setText(OdtFileLoader.loadFile(file));
					return IMPORTED;
				}
			}

			window.setText(TextFileLoader.loadFile(file));
			return WRONG_TYPE;
		} catch (IOException e) {
			return NOT_LOADED;
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