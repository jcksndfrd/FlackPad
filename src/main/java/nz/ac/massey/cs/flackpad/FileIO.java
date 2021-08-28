package nz.ac.massey.cs.flackpad;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

class FileIO {
	
	// Static variables
	final static int SAVED = 0, NOT_SAVED = 1;
	private final static int NOT_LOADED = -1, LOADED = 0, WRONG_TYPE = 1, IMPORTED = 2;

	private Window window;
	private FileMIME mime;
	
	// File IO classes
	PlainTextIO plainTextIO;
	OdtIO odtIO;
	RtfIO rtfIO;
	PdfExporter pdfExporter;

	FileIO(Window window) {
		// Save window and FileMIME instances
		this.window = window;
		mime = new FileMIME();
		
		// Save loader instances
		plainTextIO = new PlainTextIO();
		odtIO = new OdtIO();
		rtfIO = new RtfIO();
		pdfExporter = new PdfExporter();		
	}

	void open() {
		// Check if user wants to save
		int saveChoice = window.isSaved() ? 1 : Dialogs.saveWarning(window.getFileName(), window.getFrame());
		int saved = SAVED;
		
		// Do nothing if user cancels
		if (saveChoice == JOptionPane.CANCEL_OPTION || saveChoice == JOptionPane.CLOSED_OPTION) {
			return;
		}
		
		// Save if user wants to
		if (saveChoice == JOptionPane.YES_OPTION) {
			saved = save();
		}
		
		// If user saved, didn't want to or there were no changes
		if (saved == SAVED) {
			// Get file from open dialog
			JFileChooser fileChooser = new JFileChooser(window.getFile());
			if (fileChooser.showOpenDialog(window.getFrame()) == JFileChooser.APPROVE_OPTION) {
				// Load file and save load type
				int loaded = loadFile(fileChooser.getSelectedFile().getAbsoluteFile());
				
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
					Dialogs.warning("The contents of this file may not be displayed properly", window.getFrame());
				}

				// Show error dialog if file loading failed
				if (loaded == NOT_LOADED) {
					Dialogs.error("Something went wrong when loading that file", window.getFrame());
				}
			}
		}
	}

	int save() {
		// Save to current file if it exists
		if (window.getFile() != null) {
			try {
				plainTextIO.saveFile(window.getText(), window.getFile());
				window.setSaved(true);
				return SAVED;
			} catch (IOException e) {
				// Problem when saving to current file
				Dialogs.error("Something went wrong when saving that file", window.getFrame());
				return NOT_SAVED;
			}
		}
		// Otherwise open save as dialog
		return saveAs();
	}

	int saveAs() {
		// Open save as dialog and get user choice
		JFileChooser fileChooser = new JFileChooser(window.getFile());
		fileChooser.setDialogTitle("Save As");
		if (fileChooser.showSaveDialog(window.getFrame()) == JFileChooser.APPROVE_OPTION) {
			// Save file to user choice
			try {
				plainTextIO.saveFile(window.getText(), window.getFile());
				window.setFile(fileChooser.getSelectedFile().getAbsoluteFile());
				window.setSaved(true);
				return SAVED;
			} catch (IOException e) {
				// Problem when saving to chosen file
				Dialogs.error("Something went wrong when saving that file", window.getFrame());
			}
		}
		// User cancelled or error
		return NOT_SAVED;
	}

	int loadFile(File file) {
		// Get file MIME type
		String fileMIME = mime.getFileMIME(file);

		try {
			// File is plain text
			if (fileMIME.startsWith("text")) {
				window.setText(plainTextIO.loadFile(file));
				return LOADED;
			}
			
			// Check compatibility with other types
			if (fileMIME.startsWith("application")) {
				switch (fileMIME.substring(12)) {
				case "x-bat":
					window.setText(plainTextIO.loadFile(file));
					return LOADED;
				case "xml":
					window.setText(plainTextIO.loadFile(file));
					return LOADED;
				case "vnd.oasis.opendocument.text":
					window.setText(odtIO.loadFile(file));
					return IMPORTED;
				case "rtf":
					window.setText(rtfIO.loadFile(file));
					return IMPORTED;
				}
			}
			
			// Unknown type
			window.setText(plainTextIO.loadFile(file));
			return WRONG_TYPE;
		} catch (IOException e) {
			// Error loading file
			return NOT_LOADED;
		}
	}
	
	void exportToPdf(String text, String appName, File file) {
		try {
			String fileName = pdfExporter.export(text, appName, file);
			Dialogs.message("Succesfully exported as \"" + fileName + "\"", window.getFrame());
		} catch (Exception e) {
			Dialogs.error("Something went wrong when exporting to PDF", window.getFrame());
		}
	}
	
	void exportToOdt(String text, File file) {
		try {
			String fileName = odtIO.export(text, file);
			Dialogs.message("Succesfully exported as \"" + fileName + "\"", window.getFrame());
		} catch (Exception e) {
			Dialogs.error("Something went wrong when exporting to ODT", window.getFrame());
		}
	}
	
	void exportToRtf(String text, File file) {
		try {
			String fileName = rtfIO.export(text, file);
			Dialogs.message("Succesfully exported as \"" + fileName + "\"", window.getFrame());
		} catch (Exception e) {
			Dialogs.error("Something went wrong when exporting to RTF", window.getFrame());
		}
	}

}