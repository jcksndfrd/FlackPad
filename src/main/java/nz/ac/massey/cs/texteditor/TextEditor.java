package nz.ac.massey.cs.texteditor;

import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import javax.swing.event.DocumentListener;

public class TextEditor extends JFrame {
	private static final long serialVersionUID = 1L;
	private String name = "FlackPad";
	private JFrame frame;
	private ActionListener listener;
	private DocumentListener docListener;
	
	private JMenuBar menuBar;
	private JTextArea textArea;
	
	private boolean saved = true;
	private File file;
	private FileType fileType;
	private String fileName;
	
	TextEditor() {
		fileName = "Untitled";
		frame = new JFrame(fileName + " - " + name);
		listener = new Listener(this);
		docListener = new DocListener(this);
		frame.addWindowListener(new WinListener(this));
		
		menuBar = Layouts.getMenuBar(listener);
		frame.add(menuBar);
		frame.setJMenuBar(menuBar);
		
		// Text area, with listener / key bindings
		textArea = Layouts.getTextArea(docListener);
		frame.add(new JScrollPane(textArea));
		
		((DocListener) docListener).addKeyBindings();////
		
		// Window size
		frame.setSize(1000, 1000);
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
	
	JFrame getFrame() {
		return frame;
	}
	JTextArea getTextArea() {
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
	}
	
	String getFileName() {
		return fileName;
	}
	
	FileType getFileType() {
		return fileType;
	}
	
	void setFileType(FileType fileType) {
		this.fileType = fileType;
	}
	
	public static void main(String[] args) {
		new TextEditor();
	}
}
