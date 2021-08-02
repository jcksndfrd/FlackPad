package nz.ac.massey.cs.texteditor;

import java.awt.event.*;
import javax.swing.*;

public class TextEditor extends JFrame {
	JFrame frame;
	ActionListener listener;
	
	JMenuBar menuBar;
	JTextArea textArea;
	
	TextEditor() {
		frame = new JFrame();
		listener = new Listener();
		
		menuBar = Layouts.getMenuBar(listener);
		frame.add(menuBar);
		frame.setJMenuBar(menuBar);
		
		textArea = Layouts.getTextArea();
		textArea.setBounds(5, 5, 900, 900);
		frame.add(textArea);
		
		frame.setSize(1000, 1000);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		new TextEditor();
	}
}
