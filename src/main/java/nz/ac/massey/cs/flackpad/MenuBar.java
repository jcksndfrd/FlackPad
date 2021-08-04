package nz.ac.massey.cs.flackpad;

import java.awt.event.*;
import javax.swing.*;

class MenuBar extends JMenuBar {
	
	private ActionListener menuListener;
	
	MenuBar(Window window) {
		super();

		menuListener = new MenuListener(window);
		
		this.addFileMenu();
		this.addEditMenu();
		this.addHelpMenu();
	}
	
	private void addFileMenu() {
		JMenu fileMenu = new JMenu("File");
		
		JMenuItem[] fileItems = new JMenuItem[] {
				new JMenuItem("New"),
				new JMenuItem("New Window"),
				new JMenuItem("Open"),
				new JMenuItem("Save"),
				new JMenuItem("Save As"),
				new JMenuItem("Print"),
				new JMenuItem("Exit")};
		
		for (JMenuItem fileItem : fileItems) {
			fileItem.addActionListener(menuListener);
			fileMenu.add(fileItem);
		}
		
		this.add(fileMenu);
	}
	
	private void addEditMenu() {
		JMenu editMenu = new JMenu("Edit");
		
		JMenuItem[] editItems = new JMenuItem[] {
				new JMenuItem("Cut"),
				new JMenuItem("Copy"),
				new JMenuItem("Paste"),
				new JMenuItem("Delete"),
				new JMenuItem("Find"),
				new JMenuItem("Time and Date")};
		
		for (JMenuItem editItem : editItems) {
			editItem.addActionListener(menuListener);
			editMenu.add(editItem);
		}
		
		this.add(editMenu);
	}
	
	private void addHelpMenu() {
		JMenu helpMenu = new JMenu("Help");
		JMenuItem about = new JMenuItem("About");
		about.addActionListener(menuListener);
		helpMenu.add(about);
		this.add(helpMenu);
	}
	
}
