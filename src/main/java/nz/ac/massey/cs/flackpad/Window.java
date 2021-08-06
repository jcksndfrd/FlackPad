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


@SuppressWarnings("serial")
class Window extends JFrame {
	private String name = "FlackPad";
	private JFrame frame;
	private WindowListener winListener;
	private Config config;
	
	private MenuBar menuBar;
	private TextArea textArea;
	
	private boolean saved = true;
	private File file;
	private String fileName;
	private String fileMIME;
	
	Window() {
		// Create JFrame and set title
		fileName = "Untitled";
		frame = new JFrame(fileName + " - " + name);
		
		// Add icon
		frame.setIconImages(List.of(
				new ImageIcon("icons/16x16.png").getImage(),
				new ImageIcon("icons/32x32.png").getImage(),
				new ImageIcon("icons/64x64.png").getImage(),
				new ImageIcon("icons/128x128.png").getImage()));
		
		// Add window listener
		winListener = new WinListener(this);
		frame.addWindowListener(winListener);
		
		// Add menu bar
		menuBar = new MenuBar(this);
		frame.add(menuBar);
		frame.setJMenuBar(menuBar);
		
		// Add text area in a scroll pane
		textArea = new TextArea(this);
		frame.add(new JScrollPane(textArea));
		
		// Add key bindings to instance
		new KeyBinder(this);
		
		// Set window size, visibility and to not close
		frame.setSize(1000, 500);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		// Get config
		config = new Config(this);
		
		// Set configuration
		textArea.setFontWithZoom(config.getFont());
	}
	
	void newDoc() {
		int saveChoice = this.isSaved() ? 1 : Dialogs.saveWarning(this);
		int saved = FileIO.SAVED;
		
		if (saveChoice == JOptionPane.CANCEL_OPTION || saveChoice == JOptionPane.CLOSED_OPTION) return;
		if (saveChoice == JOptionPane.YES_OPTION) saved = FileIO.save(this);
		
		if (saved == FileIO.SAVED) {
			this.setFile(null);
			this.textArea.setText("");
			this.setSaved(true);
		}
	}
	
	void exit() {
		int saveChoice = this.isSaved() ? 1 : Dialogs.saveWarning(this);
		int saved = FileIO.SAVED;
		
		if (saveChoice == JOptionPane.CANCEL_OPTION || saveChoice == JOptionPane.CLOSED_OPTION) return;
		if (saveChoice == JOptionPane.YES_OPTION) saved = FileIO.save(this);
		
		if (saved == FileIO.SAVED) {
			config.saveConfigFile();
			frame.dispose();
		}
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
		fileMIME = FileIO.getFileMIME(file);
	}
	
	String getFileName() {
		return fileName;
	}
	
	String getAppName() {
		return name;
	}
	
}
