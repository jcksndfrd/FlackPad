package nz.ac.massey.cs.flackpad;

import java.awt.event.WindowListener;
import java.io.File;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

class Window {
	private String name = "FlackPad";
	private JFrame frame;
	private WindowListener winListener;
	private Config config;
	private FileMIME MIME = new FileMIME();

	private MenuBar menuBar;
	private TextArea textArea;
	private ScrollPane scrollPane;

	private boolean saved = true;
	private File file;
	private String fileName;
	private MainTheme theme;

	Window() {
		// Create JFrame and set title
		fileName = "Untitled";
		frame = new JFrame(fileName + " - " + name);

		// Add icon
		frame.setIconImages(
				List.of(new ImageIcon("icons/16x16.png").getImage(), new ImageIcon("icons/32x32.png").getImage(),
						new ImageIcon("icons/64x64.png").getImage(), new ImageIcon("icons/128x128.png").getImage()));

		
		// Add window listener
		winListener = new WinListener(this);
		frame.addWindowListener(winListener);

		// Add menu bar
		menuBar = new MenuBar(this);
		frame.add(menuBar);
		frame.setJMenuBar(menuBar);

		// Add text area in a scroll pane
		textArea = new TextArea(this);
		scrollPane = new ScrollPane(textArea);
		
		// Get config
		config = new Config(frame);
		theme = config.getTheme();
		theme.setTheme(textArea); // This must be before textArea.setTheme() and scrollPane.setTheme()
		
		// Set font and colours for textarea, scrollpane, etc.
		textArea.setTheme(config);
		scrollPane.setTheme(config);
		
		frame.add(scrollPane);

		// Add key bindings to instance
		new KeyBinder(this);

		// Set window size, visibility and to not close
		frame.setSize(1000, 500);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setVisible(true);

		frame.requestFocus();
		textArea.grabFocus();

	}

	void toggleTheme() {
		switch (theme.getThemeString()) {
		case "dark":
			theme.setThemeString("light");
			theme.setTheme(textArea);
			scrollPane.setTheme(config);
			textArea.setTheme(config);

			break;
		case "light":
			theme.setThemeString("dark");
			theme.setTheme(textArea);
			scrollPane.setTheme(config);
			textArea.setTheme(config);
			break;
		}
	}

	void newDoc() {
		int saveChoice = this.isSaved() ? 1 : Dialogs.saveWarning(fileName, frame);
		int saved = FileIO.SAVED;

		if (saveChoice == JOptionPane.CANCEL_OPTION || saveChoice == JOptionPane.CLOSED_OPTION)
			return;
		if (saveChoice == JOptionPane.YES_OPTION)
			saved = FileIO.save(this);

		if (saved == FileIO.SAVED) {
			this.setFile(null);
			this.textArea.setText("");
			this.setSaved(true);
		}
	}

	void exit() {
		int saveChoice = this.isSaved() ? 1 : Dialogs.saveWarning(fileName, frame);
		int saved = FileIO.SAVED;

		if (saveChoice == JOptionPane.CANCEL_OPTION || saveChoice == JOptionPane.CLOSED_OPTION)
			return;
		if (saveChoice == JOptionPane.YES_OPTION)
			saved = FileIO.save(this);

		if (saved == FileIO.SAVED) {
			config.saveConfigFile();
			frame.dispose();
		}
	}

	void gutterToggle() {
		scrollPane.setLineNumbersEnabled(!scrollPane.getLineNumbersEnabled());
	}

	void setInformationBar(String val) {
		menuBar.setInformationBarText(val);
	}

	void setInformationBarZoomText(String val) {
		menuBar.setInformationBarZoomText(val);
	}

	public void setInformationBarZoomVisible(boolean isVisible) {
		menuBar.setInformationBarZoomVisible(isVisible);
	}
	JScrollPane getLineScrollPane() {
		return scrollPane;
	}

	JTextField getFindField() {
		return menuBar.getFindField();
	}

	JButton getFindClose() {
		return menuBar.getFindClose();
	}

	void showFindBar() {
		menuBar.showFindBar();
	}

	void hideFindBar() {
		menuBar.hideFindBar();
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
		frame.setTitle((saved ? "" : "*") + fileName + " - " + name);
		this.saved = saved;
	}

	File getFile() {
		return file;
	}

	void setFile(File file) {
		this.file = file;
		fileName = file == null ? "Untitled" : file.getName();
		textArea.setSyntaxEditingStyle(MIME.getFileStyle(file));
		menuBar.setInformationBarFileText(MIME.getFileStyle(file));
	}

	String getFileName() {
		return fileName;
	}

	String getAppName() {
		return name;
	}

	void setText(String text) {
		textArea.setText(text);
	}
	
	String getText() {
		return textArea.getText();
	}

	public void zoomIn() {
		textArea.zoomIn();
	}

	public void zoomOut() {
		textArea.zoomOut();
	}

	public void resetZoom() {
		textArea.resetZoom();
	}

	public void addTimeAndDate() {
		textArea.addTimeAndDate();
	}
}
