package nz.ac.massey.cs.flackpad;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.itextpdf.text.DocumentException;

class FileIO {

	// Static variables
	final static int SAVED = 0;
	final static int NOT_SAVED = 1;

	private final static int NOT_LOADED = -1;
	private final static int LOADED = 0;
	private final static int WRONG_TYPE = 1;
	private final static int IMPORTED = 2;

	private final Window window;
	private final FileMime mime;

	FileIO(Window window) {
		// Save window and FileMIME instances
		this.window = window;
		mime = new FileMime();
	}

	void open() {
		if (saveWarning() == SAVED) {
			openDialog();
		}
	}

	private void openDialog() {
		// Get file from open dialog
		final JFileChooser fileChooser = new JFileChooser(window.getFile());
		if (fileChooser.showOpenDialog(window.getFrame()) == JFileChooser.APPROVE_OPTION) {
			// Load file and save load type
			final int loaded = loadFile(fileChooser.getSelectedFile().getAbsoluteFile());

			// File loaded
			if (loaded == LOADED || loaded == WRONG_TYPE || loaded == IMPORTED) {
				window.getTextArea().setCaretPosition(0);
				window.getTextArea().discardAllEdits();
				window.updateUndoRedoEnable();
				window.updateCCDEnable();
			}

			// Editing actual file
			if (loaded == LOADED || loaded == WRONG_TYPE) {
				window.setFile(fileChooser.getSelectedFile().getAbsoluteFile());
				window.setSaved(true);
			}

			// Warn user that file is not supported
			if (loaded == WRONG_TYPE) {
				DialogUtils.warning("The contents of this file may not be displayed properly", window.getFrame());
			}

			// Show error dialog if file loading failed
			if (loaded == NOT_LOADED) {
				DialogUtils.error("Something went wrong when loading that file", window.getFrame());
			}
		}
	}

	int save() {
		int saveStatus = NOT_SAVED;

		// Save to current file if it exists otherwise open save as dialog
		if (window.getFile() == null) {
			saveStatus = saveAs();
		} else {
			try {
				PlainTextUtils.saveFile(window.getText(), window.getFile());
				window.setSaved(true);
				saveStatus = SAVED;
			} catch (IOException error) {
				// Problem when saving to current file
				DialogUtils.error("Something went wrong when saving that file", window.getFrame());
			}
		}

		// Return saved status
		return saveStatus;
	}

	int saveAs() {
		int saveStatus = NOT_SAVED;

		// Open save as dialog and get user choice
		final SaveAsChooser fileChooser = new SaveAsChooser(window.getFile());
		fileChooser.setDialogTitle("Save As");
		if (fileChooser.showSaveDialog(window.getFrame()) == JFileChooser.APPROVE_OPTION) {
			// Save file to user choice
			try {
				PlainTextUtils.saveFile(window.getText(), fileChooser.getSelectedFile().getAbsoluteFile());
				window.setFile(fileChooser.getSelectedFile().getAbsoluteFile());
				window.setSaved(true);
				saveStatus = SAVED;
			} catch (IOException error) {
				// Problem when saving to chosen file
				DialogUtils.error("Something went wrong when saving that file", window.getFrame());
			}
		}

		// Return saved status
		return saveStatus;
	}

	int loadFile(File file) {
		int loadStatus = NOT_LOADED;

		// Get file MIME type
		final String fileMime = mime.getFileMime(file);

		try {
			// File is plain text
			if (fileMime.startsWith("text")) {
				window.setText(PlainTextUtils.loadFile(file));
				loadStatus = LOADED;
			}

			// Check compatibility with other types
			if (fileMime.startsWith("application")) {
				switch (fileMime.substring(12)) {
				case "x-bat":
					window.setText(PlainTextUtils.loadFile(file));
					loadStatus = LOADED;
					break;
				case "xml":
					window.setText(PlainTextUtils.loadFile(file));
					loadStatus = LOADED;
					break;
				case "vnd.oasis.opendocument.text":
					window.setText(OdtUtils.loadFile(file));
					loadStatus = IMPORTED;
					break;
				case "rtf":
					window.setText(RtfUtils.loadFile(file));
					loadStatus = IMPORTED;
					break;
				}
			}

			// Unknown type
			if (loadStatus == NOT_LOADED) {
				window.setText(PlainTextUtils.loadFile(file));
				loadStatus = WRONG_TYPE;
			}

		} catch (IOException | BadLocationException | SAXException | ParserConfigurationException error) {
			// Error loading file
			loadStatus = NOT_LOADED;
		}

		// return load status
		return loadStatus;
	}

	int saveWarning() {
		// Check if user wants to save
		final int saveChoice = window.isSaved() ? 1 : DialogUtils.saveWarning(window.getFileName(), window.getFrame());

		// Do nothing if cancelled
		int saveStatus = NOT_SAVED;

		if (saveChoice == JOptionPane.NO_OPTION) {
			// User doesn't want to save
			saveStatus = SAVED;
		} else if (saveChoice == JOptionPane.YES_OPTION) {
			// User wants to save
			saveStatus = save();
		}
		
		return saveStatus;
	}

	void exportToPdf(String text, String appName, File file) {
		try {
			PdfUtils.export(text, appName, file);
			DialogUtils.message("Succesfully exported as \"" + file.getName() + "\"", window.getFrame());
		} catch (FileNotFoundException | UnknownHostException | DocumentException error) {
			DialogUtils.error("Something went wrong when exporting to PDF", window.getFrame());
		}
	}

	void exportToOdt(String text, File file) {
		try {
			OdtUtils.export(text, file);
			DialogUtils.message("Succesfully exported as \"" + file.getName() + "\"", window.getFrame());
		} catch (Exception error) {
			DialogUtils.error("Something went wrong when exporting to ODT", window.getFrame());
		}
	}

	void exportToRtf(String text, File file) {
		try {
			RtfUtils.export(text, file);
			DialogUtils.message("Succesfully exported as \"" + file.getName() + "\"", window.getFrame());
		} catch (IOException | BadLocationException error) {
			DialogUtils.error("Something went wrong when exporting to RTF", window.getFrame());
		}
	}

}