package nz.ac.massey.cs.flackpad;

import java.awt.event.*;
import java.io.File;
import javax.swing.*;

@SuppressWarnings("serial")
class Window extends JFrame {
	private String name = "FlackPad";
	private JFrame frame;
	private WindowListener winListener;
	
	private MenuBar menuBar;
	private TextArea textArea;
	
	private boolean saved = true;
	private File file;
	private String fileName;
	private String fileMIME;
	
	Window() {
		fileName = "Untitled";
		frame = new JFrame(fileName + " - " + name);
		
		winListener = new WinListener(this);
		frame.addWindowListener(winListener);
		
		menuBar = new MenuBar(this);
		frame.add(menuBar);
		frame.setJMenuBar(menuBar);
		
		textArea = new TextArea(this);
		frame.add(new JScrollPane(textArea));
		
		// Add key bindings to instance
		new KeyBinder(this);
				
		frame.setSize(1000, 500);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
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
//		System.out.println(fileMIME);
	}
	
	String getFileName() {
		return fileName;
	}
	
	String getAppName() {
		return name;
	}
	
}