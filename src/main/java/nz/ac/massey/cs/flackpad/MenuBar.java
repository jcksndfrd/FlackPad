package nz.ac.massey.cs.flackpad;

import java.awt.Color;
import java.awt.event.*;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

@SuppressWarnings("serial")
class MenuBar extends JMenuBar {

	private ActionListener menuListener;
	private Color menuBackground = Color.decode("#ffffff");
	private Color menuItemTabForeground = Color.decode("#555555");

	// File menu items
	private JMenuItem newItem;
	private JMenuItem newWindowItem;
	private JMenuItem openItem;
	private JMenuItem saveItem;
	private JMenuItem saveAsItem;
	private JMenuItem printItem;
	private JMenuItem pdfItem;
	private JMenuItem exitItem;

	// Edit menu items
	private JMenuItem undoItem;
	private JMenuItem redoItem;
	private JMenuItem cutItem;
	private JMenuItem copyItem;
	private JMenuItem pasteItem;
	private JMenuItem selectAllItem;
	private JMenuItem deleteItem;
	private JMenuItem findItem;
	private JMenuItem tDItem;

	// View menu items
	private JMenuItem zoomInItem;
	private JMenuItem zoomOutItem;
	private JMenuItem resetZoomItem;
	private JMenuItem lineNumbersItem;
	private JMenuItem themeItem;
	private JMenuItem themeDialogItem;

	// Help menu items
	private JMenuItem aboutItem;

	MenuBar(Window window) {
		super();

		menuListener = new MenuListener(window);

		addFileMenu();
		addEditMenu();
		addViewMenu();
		addHelpMenu();
		setBackground(menuBackground);

	}
	public ActionListener getMenuListener() {
		return menuListener;
	} 

	private void addFileMenu() {
		JMenu fileMenu = new JMenu("File");
		fileMenu.setForeground(menuItemTabForeground);
		fileMenu.setMnemonic(KeyEvent.VK_F);

		newItem = new JMenuItem("New");
		newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
		newItem.addActionListener(menuListener);
		fileMenu.add(newItem);

		newWindowItem = new JMenuItem("New Window");
		newWindowItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
		newWindowItem.addActionListener(menuListener);
		fileMenu.add(newWindowItem);

		openItem = new JMenuItem("Open");
		openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
		openItem.addActionListener(menuListener);
		fileMenu.add(openItem);

		saveItem = new JMenuItem("Save");
		saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
		saveItem.addActionListener(menuListener);
		fileMenu.add(saveItem);

		saveAsItem = new JMenuItem("Save As...");
		saveAsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
		saveAsItem.addActionListener(menuListener);
		fileMenu.add(saveAsItem);

		printItem = new JMenuItem("Print");
		printItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK));
		printItem.addActionListener(menuListener);
		fileMenu.add(printItem);

		pdfItem = new JMenuItem("Export as...");
		pdfItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
		pdfItem.addActionListener(menuListener);
		fileMenu.add(pdfItem);

		exitItem = new JMenuItem("Exit");
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_DOWN_MASK));
		exitItem.addActionListener(menuListener);
		fileMenu.add(exitItem);

		add(fileMenu);
		
	}

	private void addEditMenu() {
		JMenu editMenu = new JMenu("Edit");
		editMenu.setForeground(menuItemTabForeground);
		editMenu.setMnemonic(KeyEvent.VK_E);

		undoItem = new JMenuItem("Undo");
		undoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
		undoItem.addActionListener(menuListener);
		editMenu.add(undoItem);

		redoItem = new JMenuItem("Redo");
		redoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_DOWN_MASK));
		redoItem.addActionListener(menuListener);
		editMenu.add(redoItem);

		cutItem = new JMenuItem("Cut");
		cutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
		cutItem.addActionListener(menuListener);
		editMenu.add(cutItem);

		copyItem = new JMenuItem("Copy");
		copyItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
		copyItem.addActionListener(menuListener);
		editMenu.add(copyItem);

		pasteItem = new JMenuItem("Paste");
		pasteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
		pasteItem.addActionListener(menuListener);
		editMenu.add(pasteItem);

		selectAllItem = new JMenuItem("Select All");
		selectAllItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
		selectAllItem.addActionListener(menuListener);
		editMenu.add(selectAllItem);

		deleteItem = new JMenuItem("Delete");
		deleteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
		deleteItem.addActionListener(menuListener);
		editMenu.add(deleteItem);

		findItem = new JMenuItem("Find");
		findItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK));
		findItem.addActionListener(menuListener);
		editMenu.add(findItem);

		tDItem = new JMenuItem("Time and Date");
		tDItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
		tDItem.addActionListener(menuListener);
		editMenu.add(tDItem);

		add(editMenu);
	}

	private void addViewMenu() {
		JMenu viewMenu = new JMenu("View");
		viewMenu.setForeground(menuItemTabForeground);
		viewMenu.setMnemonic(KeyEvent.VK_V);

		zoomInItem = new JMenuItem("Zoom In");
		zoomInItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_9, InputEvent.CTRL_DOWN_MASK));
		zoomInItem.addActionListener(menuListener);
		viewMenu.add(zoomInItem);

		zoomOutItem = new JMenuItem("Zoom Out");
		zoomOutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_8, InputEvent.CTRL_DOWN_MASK));
		zoomOutItem.addActionListener(menuListener);
		viewMenu.add(zoomOutItem);

		resetZoomItem = new JMenuItem("Reset Zoom");
		resetZoomItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_0, InputEvent.CTRL_DOWN_MASK));
		resetZoomItem.addActionListener(menuListener);
		viewMenu.add(resetZoomItem);

		lineNumbersItem = new JMenuItem("Line Numbers");
		lineNumbersItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_DOWN_MASK));
		lineNumbersItem.addActionListener(menuListener);
		viewMenu.add(lineNumbersItem);

		themeItem = new JMenuItem("Toggle Theme");
		themeItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_DOWN_MASK));
		themeItem.addActionListener(menuListener);
		viewMenu.add(themeItem);
		
		themeDialogItem = new JMenuItem("Font and Theme");
		themeDialogItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
		themeDialogItem.addActionListener(menuListener);
		viewMenu.add(themeDialogItem);

		add(viewMenu);
	}

	private void addHelpMenu() {
		JMenu helpMenu = new JMenu("Help");
		helpMenu.setForeground(menuItemTabForeground);
		helpMenu.setMnemonic(KeyEvent.VK_H);

		aboutItem = new JMenuItem("About");
		aboutItem.addActionListener(menuListener);
		helpMenu.add(aboutItem);

		add(helpMenu);
	}

	void setUndoEnabled(Boolean enabled) {
		undoItem.setEnabled(enabled);
	}
	
	void setRedoEnabled(Boolean enabled) {
		redoItem.setEnabled(enabled);
	}
	
	void setCCDEnabled(Boolean enabled) {
		cutItem.setEnabled(enabled);
		copyItem.setEnabled(enabled);
		deleteItem.setEnabled(enabled);
	}
	
	void setZoomInEnabled(Boolean enabled) {
		zoomInItem.setEnabled(enabled);
	}
	
	void setZoomOutEnabled(Boolean enabled) {
		zoomOutItem.setEnabled(enabled);
	}
	
	void setResetZoomEnabled(Boolean enabled) {
		resetZoomItem.setEnabled(enabled);
	}

}
