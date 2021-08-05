package nz.ac.massey.cs.flackpad;

import java.awt.Dimension;
import java.awt.event.*;
import java.util.LinkedHashMap;

import javax.swing.*;

@SuppressWarnings("serial")
class MenuBar extends JMenuBar {
	
	private ActionListener menuListener;
	private JTextField findField;
	private JButton exitFindButton;
	private TextArea textarea;
	
	MenuBar(Window window) {
		super();

		menuListener = new MenuListener(window);
		this.textarea = window.getTextArea();
		
		this.addFileMenu();
		this.addEditMenu();
		this.addHelpMenu();
		
		this.addFindBar();
	}
	
	private void addFileMenu() {
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		
		LinkedHashMap<String, KeyStroke> fileItems = new LinkedHashMap<String, KeyStroke>();
		fileItems.put("New", KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
		fileItems.put("New Window", KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK+InputEvent.SHIFT_DOWN_MASK));
		fileItems.put("Open", KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
		fileItems.put("Save", KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
		fileItems.put("Save As", KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK+InputEvent.SHIFT_DOWN_MASK));
		fileItems.put("Print", KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK));
		fileItems.put("Export to PDF", null);
		fileItems.put("Exit", KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_DOWN_MASK));
		
		for (String itemName : fileItems.keySet()) {
			JMenuItem item = new JMenuItem(itemName);
			if (fileItems.get(itemName) != null) item.setAccelerator(fileItems.get(itemName));
			item.addActionListener(menuListener);
			fileMenu.add(item);
		}
		
		this.add(fileMenu);
	}
	
	private void addEditMenu() {
		JMenu editMenu = new JMenu("Edit");
		editMenu.setMnemonic(KeyEvent.VK_E);
		
		LinkedHashMap<String, KeyStroke> editItems = new LinkedHashMap<String, KeyStroke>();
		editItems.put("Cut", KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
		editItems.put("Copy", KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
		editItems.put("Paste", KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
		editItems.put("Select All",  KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
		editItems.put("Delete", KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
		editItems.put("Find", KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK));
		editItems.put("Time and Date", KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
		
		for (String itemName : editItems.keySet()) {
			JMenuItem item = new JMenuItem(itemName);
			item.setAccelerator(editItems.get(itemName));
			item.addActionListener(menuListener);
			editMenu.add(item);
		}
		
		this.add(editMenu);
	}
	
	private void addHelpMenu() {
		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);
		JMenuItem about = new JMenuItem("About");
		about.addActionListener(menuListener);
		helpMenu.add(about);
		this.add(helpMenu);
	}
	
	private void addFindBar() {
		exitFindButton = new JButton("X");
		exitFindButton.setVisible(false);
		this.add(exitFindButton);
		
		findField = new JTextField("");
		findField.setVisible(false);
		findField.addActionListener(menuListener);
		this.add(findField);

	}
	public JTextField getFindField() {
		return findField;
	}
	public JButton getFindClose() {
		return exitFindButton;
	}
		
}
