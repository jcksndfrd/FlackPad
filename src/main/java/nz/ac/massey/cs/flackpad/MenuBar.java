package nz.ac.massey.cs.flackpad;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import java.util.LinkedHashMap;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

@SuppressWarnings("serial")
class MenuBar extends JMenuBar {
	
	private ActionListener menuListener;
	private JTextField findField;
	private JButton exitFindButton;
	private TextArea textarea;
	private Window window;
	
	MenuBar(Window window) {
		super();

		menuListener = new MenuListener(window);
		this.textarea = window.getTextArea();
		this.window = window;
		
		this.addFileMenu();
		this.addEditMenu();
		this.addViewMenu();
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
	
	private void addViewMenu() {
		JMenu viewMenu = new JMenu("View");
		viewMenu.setMnemonic(KeyEvent.VK_V);
		
		LinkedHashMap<String, KeyStroke> viewItems = new LinkedHashMap<String, KeyStroke>();
		viewItems.put("Zoom In", KeyStroke.getKeyStroke(KeyEvent.VK_CLOSE_BRACKET, InputEvent.CTRL_DOWN_MASK));
		viewItems.put("Zoom Out", KeyStroke.getKeyStroke(KeyEvent.VK_OPEN_BRACKET, InputEvent.CTRL_DOWN_MASK));
		viewItems.put("Reset Zoom", KeyStroke.getKeyStroke(KeyEvent.VK_0, InputEvent.CTRL_DOWN_MASK));
		
		for (String itemName : viewItems.keySet()) {
			JMenuItem item = new JMenuItem(itemName);
			item.setAccelerator(viewItems.get(itemName));
			item.addActionListener(menuListener);
			viewMenu.add(item);
		}
		
		this.add(viewMenu);
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
		
		addListenersToFindBar();
	}
	private void addListenersToFindBar() {		
		exitFindButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				hideFindBar();	
			}
		});
		
		// Set search listener
		findField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					TextArea textarea = window.getTextArea();
	
					String searchtext = findField.getText();
					String text = textarea.getText();
					
					int offset = text.indexOf(searchtext);
					int length = searchtext.length();
					
					// Remove all current highlights
					textarea.getHighlighter().removeAllHighlights();
			    
					Highlighter.HighlightPainter painter = 
						    new DefaultHighlighter.DefaultHighlightPainter( Color.cyan );
					
					// Get all occurrences
					while ( offset != -1)
					{
					    try
					    {
					    	textarea.getHighlighter().addHighlight(offset, offset + length, painter);
					        offset = text.indexOf(searchtext, offset + 1);
					    }
					    catch(Exception e1) { 
							Dialogs.error("Could not highlight search phrase", window);
					    }
					}
				
				} catch (Exception err) {
					Dialogs.error("Issue with find / replace listeners", window);
				}
			}
		});		
	}
	public void hideFindBar() {
		try {
			findField.setVisible(false);
			exitFindButton.setVisible(false);
			TextArea textarea = window.getTextArea();
			textarea.getHighlighter().removeAllHighlights();
		} catch (Exception e) {
			Dialogs.error("Could not hide search bar", window);
		}
		
	}
	public void showFindBar() {
		try {
			findField.setVisible(true);
			exitFindButton.setVisible(true);
			findField.requestFocus();
		} catch (Exception e) {
			Dialogs.error("Could not show search bar", window);
		}
	}
	public JTextField getFindField() {
		return findField;
	}
	public JButton getFindClose() {
		return exitFindButton;
	}
		
}
