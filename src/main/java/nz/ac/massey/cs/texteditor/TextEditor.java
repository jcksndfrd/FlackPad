package nz.ac.massey.cs.texteditor;

import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class TextEditor extends JFrame {
	private JFrame frame;
	private ActionListener listener;
	
	private JMenuBar menuBar;
	private JTextArea textArea;
	
	private boolean saved = true;
	private File file;
	private FileType fileType;
	
	TextEditor() {
		frame = new JFrame();
		listener = new Listener(this);
		
		menuBar = Layouts.getMenuBar(listener);
		frame.add(menuBar);
		frame.setJMenuBar(menuBar);
		
		textArea = Layouts.getTextArea();
		frame.add(new JScrollPane(textArea));
		
		frame.setSize(1000, 1000);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	TextEditor(JFrame window) {
		try {
			// Remove current window
	        window.dispose();
	        
	        // Create new instance
			new TextEditor();
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	public JFrame getFrame() {
		return frame;
	}
	public JTextArea getTextArea() {
		return textArea;
	}
	
	public boolean isSaved() {
		return saved;
	}
	
	public void setSaved(boolean saved) {
		this.saved = saved;
	}
	
	public File getFile() {
		return file;
	}
	
	public void setFile(File file) {
		this.file = file;
	}
	
	public FileType getFileType() {
		return fileType;
	}
	
	public void setFileType(FileType fileType) {
		this.fileType = fileType;
	}
	
	public static void main(String[] args) {
		new TextEditor();
	}
}
