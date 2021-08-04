package nz.ac.massey.cs.flackpad;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.Toolkit;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

public class KeyBinder {
	private Window windowInstance;
	KeyBinder(Window windowInstance) {
		this.windowInstance = windowInstance;
		addKeyBindings();
	}
	
	private void addKeyBindings() {

		TextArea textArea =  windowInstance.getTextArea();

        // Common key shortcuts
        KeyStroke ctrl_s = KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK);
        KeyStroke ctrl_c = KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK);
        KeyStroke ctrl_v = KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK);
        KeyStroke ctrl_x = KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK);

        /*** Actions ***/
        
        // Save action
    	Action save = new AbstractAction() {
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e) {
				FileIO.save(windowInstance);
    	    }
    	};
    	
        // Copy action
    	Action copy = new AbstractAction() {
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e) {
    	    	try {
    	    		StringSelection stringSelection = new StringSelection(textArea.getSelectedText());
    	    		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    	    		clipboard.setContents(stringSelection, null);
    	    	}
    	    	catch (Exception err) {
    				Dialogs.error("Could not copy selected text to clipboard", windowInstance);
    	    	}
    	    }
    	};
    	
        // Cut action
    	Action cut = new AbstractAction() {
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e) {
    	    	try {
    	    		StringSelection stringSelection = new StringSelection(textArea.getSelectedText());
    	    		textArea.replaceSelection("");
    	    		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    	    		clipboard.setContents(stringSelection, null);
    	    	}
    	    	catch (Exception err) {
    				Dialogs.error("Could not cut selected", windowInstance);
    	    	}
    	    }
    	};
    	
        // Paste action
    	Action paste = new AbstractAction() {
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e) {
    	    	try {
    	    		/*
    	    		 * Handle 2 cases:
    	    		 * a. Paste with text selected 
    	    		 * b. Paste with no text selected
    	    		 */
    	    		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    	    		Transferable data = clipboard.getContents(null);
    	    		
    	    	    String plaintext = (String) data.getTransferData(DataFlavor.stringFlavor);
    	    		
    	    		if (textArea.getSelectedText() != null) {
    	    			// .a
        	    		textArea.replaceSelection(plaintext);
    	    		} else {
    	    			// .b
    	    			textArea.insert(plaintext, textArea.getCaretPosition());
    	    		}
    	    	}
    	    	catch (Exception err) {
    				Dialogs.error("Could not paste from clipboard", windowInstance);
    	    	}
    	    }
    	};
    	
    	// Adding key bindings
    	makeBinding(textArea, ctrl_s, "save_msg", save);
    	makeBinding(textArea, ctrl_c, "copy_msg", copy);
    	makeBinding(textArea, ctrl_v, "paste_msg", paste);
    	makeBinding(textArea, ctrl_x, "cut_msg", cut);
    	
	}
	
	private void makeBinding(TextArea area, KeyStroke key, String msg, Action act) {
		area.getInputMap().put(key, msg);    
        area.getActionMap().put(msg, act);  
	}
}

