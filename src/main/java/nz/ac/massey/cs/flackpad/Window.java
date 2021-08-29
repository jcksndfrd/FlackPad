package nz.ac.massey.cs.flackpad;

import java.awt.Image;
import java.awt.print.PrinterException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;

final class Window {
	private final String appName = "FlackPad";
	private final Config config;
	private final FileMime mime = new FileMime();
	private final FileIO fileIO;

	// Swing components
	private final JFrame frame;
	private final InformationBar informationBar;
	private final MenuBar menuBar;
	private final TextArea textArea;
	private final SearchBar searchBar;
	private final ScrollPane scrollPane;

	// State variables
	private boolean saved = true;
	private File file;
	private String fileName = "Untitled";

	Window() {
		// Create JFrame and set title
		frame = new JFrame(fileName + " - " + appName);

		// Add icons
		final List<Image> iconList = new ArrayList<>();
		iconList.add(new ImageIcon(getClass().getResource("/icons/16x16.png")).getImage());
		iconList.add(new ImageIcon(getClass().getResource("/icons/32x32.png")).getImage());
		iconList.add(new ImageIcon(getClass().getResource("/icons/64x64.png")).getImage());
		iconList.add(new ImageIcon(getClass().getResource("/icons/128x128.png")).getImage());
		frame.setIconImages(iconList);

		// Add window listener
		frame.addWindowListener(new WinListener(this));

		// Add menu bar
		menuBar = new MenuBar(this);
		frame.add(menuBar);
		frame.setJMenuBar(menuBar);

		// Add text area in a scroll pane
		textArea = new TextArea(this);
		scrollPane = new ScrollPane(textArea);
		searchBar = new SearchBar(textArea, menuBar);
		informationBar = new InformationBar(menuBar);

		// Add FileIO instance
		fileIO = new FileIO(this);

		// Get config
		config = new Config(frame);

		// Set font and colours for textarea, scrollpane, etc.
		textArea.setTheme(config);
		scrollPane.setTheme(config);
		informationBar.setTheme(config);
		frame.getContentPane().setBackground(config.getTheme().getSyntaxTheme().bgColor);

		frame.getContentPane().add(scrollPane);

		// Enable/disable menu items
		updateUndoRedoEnable();
		updateCCDEnable();
		updateZoomEnable();

		// Add key bindings to instance
		new KeyBinder(this);

		// Set window size, visibility and to not close
		frame.setSize(1000, 500);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setVisible(true);

		frame.requestFocus();
		textArea.grabFocus();

	}

	void newDoc() {
		if (fileIO.saveWarning() == FileIO.SAVED) {
			// Clear text area
			setFile(null);
			textArea.setText("");
			setSaved(true);
			textArea.discardAllEdits();

			// Enable/disable menu items
			updateUndoRedoEnable();
			updateCCDEnable();
		}
	}

	void exit() {
		if (fileIO.saveWarning() == FileIO.SAVED) {
			// Save config file and dispose frame
			config.saveConfigFile();
			frame.dispose();
		}
	}

	void toggleTheme() {
		final String currentThemeName = config.getTheme().getThemeName();
		config.setTheme("light".equals(currentThemeName) ? "dark" : "light");
		textArea.setTheme(config);
		scrollPane.setTheme(config);
		informationBar.setTheme(config);
		frame.getContentPane().setBackground(config.getTheme().getSyntaxTheme().bgColor);
	}

	void openThemeDialog() {
		final ThemeDialog themeDialog = new ThemeDialog(frame, config.getFont(), config.getThemeName(),
				config.getDefaultFont(), config.getDefaultThemeName());
		if (themeDialog.showDialog() == ThemeDialog.SAVE_OPTION) {
			config.setFont(themeDialog.getFontChoice());
			config.setTheme(themeDialog.getThemeChoice());
			textArea.setTheme(config);
			scrollPane.setTheme(config);
			informationBar.setTheme(config);
			frame.getContentPane().setBackground(config.getTheme().getSyntaxTheme().bgColor);
		}
	}

	void export() {
		if (getText().length() == 0) {
			DialogUtils.message("There is nothing to export", frame);
		} else {
			final ExportDialog exportDialog = new ExportDialog(frame, file, fileName);
			if (exportDialog.showDialog() == ExportDialog.EXPORT_OPTION) {
				switch (exportDialog.getFormatChoice()) {
				case ".pdf":
					fileIO.exportToPdf(textArea.getText(), appName, exportDialog.getFileChoice());
					break;
				case ".odt":
					fileIO.exportToOdt(textArea.getText(), exportDialog.getFileChoice());
					break;
				case ".rtf":
					fileIO.exportToRtf(textArea.getText(), exportDialog.getFileChoice());
					break;
				}
			}
		}
	}

	void gutterToggle() {
		scrollPane.setLineNumbersEnabled(!scrollPane.getLineNumbersEnabled());
	}

	void print() {
		final Printer printer = new Printer();
		try {
			printer.printString(getText(), config.getFont());
		} catch (PrinterException error) {
			DialogUtils.error("Something went wrong when printing", frame);
		}
	}

	void undo() {
		textArea.undoLastAction();
	}

	void redo() {
		textArea.redoLastAction();
	}

	void addTimeAndDate() {
		try {
			textArea.addTimeAndDate();
		} catch (BadLocationException error) {
			DialogUtils.error("Something went wrong when getting the time and date", frame);
		}
	}

	void zoomIn() {
		textArea.zoomIn();
		updateZoomEnable();
	}

	void zoomOut() {
		textArea.zoomOut();
		updateZoomEnable();
	}

	void resetZoom() {
		textArea.resetZoom();
		updateZoomEnable();
	}

	void updateUndoRedoEnable() {
		menuBar.setUndoEnabled(textArea.canUndo());
		menuBar.setRedoEnabled(textArea.canRedo());
	}

	void updateCCDEnable() {
		menuBar.setCCDEnabled(textArea.getSelectedText() != null);
	}

	void updateZoomEnable() {
		menuBar.setZoomInEnabled(textArea.getZoomPercentage() < 1000);
		menuBar.setZoomOutEnabled(textArea.getZoomPercentage() > 50);
		menuBar.setResetZoomEnabled(textArea.getZoomPercentage() != 100);

		if (textArea.getZoomPercentage() == 100) {
			informationBar.setInformationBarZoomVisible(false);
		} else {
			informationBar.setInformationBarZoomText(Integer.toString(textArea.getZoomPercentage()) + "%");
			informationBar.setInformationBarZoomVisible(true);
		}
	}

	void setInformationBar(String val) {
		informationBar.setInformationBarText(val);
	}

	void updateInformationBar() {
		if (getText() == null) {
			informationBar.setInformationBarText("0" + " | Char");
		} else {
			informationBar.setInformationBarText(Integer.toString(getText().length()) + " | Char");
		}
	}

	SearchBar getSearchBar() {
		return searchBar;
	}

	JTextField getFindField() {
		return searchBar.getFindField();
	}

	JButton getFindClose() {
		return searchBar.getFindClose();
	}

	void showFindBar() {
		searchBar.showFindBar();
	}

	void hideFindBar() {
		searchBar.hideFindBar();
	}

	JFrame getFrame() {
		return frame;
	}

	TextArea getTextArea() {
		return textArea;
	}

	boolean isSaved() {
		return saved;
	}

	void setSaved(boolean saved) {
		frame.setTitle((saved ? "" : "*") + fileName + " - " + appName);
		this.saved = saved;
	}

	File getFile() {
		return file;
	}

	FileIO getFileIO() {
		return fileIO;
	}

	void setFile(File file) {
		// Set file and file name used in window title
		this.file = file;
		fileName = file == null ? "Untitled" : file.getName();
		// Set syntax highlighting and info bar text based on file type
		textArea.setSyntaxEditingStyle(mime.getFileStyle(file));
		informationBar.setInformationBarFileText(mime.getFileStyle(file));
		// Clear undo/redo history
		textArea.discardAllEdits();
	}

	String getFileName() {
		return fileName;
	}

	String getAppName() {
		return appName;
	}

	void setText(String text) {
		textArea.setText(text);
	}

	String getText() {
		return textArea.getText() == null ? "" : textArea.getText();
	}

	ImageIcon getIcon() {
		return new ImageIcon(getClass().getResource("/icons/64x64.png"));
	}

}