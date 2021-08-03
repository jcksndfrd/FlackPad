package nz.ac.massey.cs.texteditor;

import java.awt.event.*;
import javax.swing.*;

public final class Layouts {
	
	private Layouts() {
		throw new UnsupportedOperationException();
	}
	
	public static JMenuBar getMenuBar(ActionListener listener) {
		JMenuBar menuBar = new JMenuBar();
		
		// Create main navigation menu
		JMenu file = new JMenu("File");
		JMenuItem[] fileItems = new JMenuItem[] {
				new JMenuItem("New"),
				new JMenuItem("New Window"),
				new JMenuItem("Open"),
				new JMenuItem("Save"),
				new JMenuItem("Save As"),
				new JMenuItem("Print"),
				new JMenuItem("Exit")};
		for (JMenuItem fileItem:fileItems) {
			fileItem.addActionListener(listener);
			file.add(fileItem);
		}
		
		// Create edit navigation menu
		JMenu edit = new JMenu("Edit");
		JMenuItem[] editItems = new JMenuItem[] {
				new JMenuItem("Cut"),
				new JMenuItem("Copy"),
				new JMenuItem("Paste"),
				new JMenuItem("Delete"),
				new JMenuItem("Find"),
				new JMenuItem("Time and Date")};
		// Add listeners to items
		for (JMenuItem editItem:editItems) {
			editItem.addActionListener(listener);
			edit.add(editItem);
		}
		
		JMenu help = new JMenu("Help");
		JMenuItem about = new JMenuItem("About");
		about.addActionListener(listener);
		help.add(about);
		
		menuBar.add(file);
		menuBar.add(edit);
		menuBar.add(help);
		
		return menuBar;
	}
	
	public static JTextArea getTextArea() {
		JTextArea textArea = new JTextArea();
		
		return textArea;
	}
}
