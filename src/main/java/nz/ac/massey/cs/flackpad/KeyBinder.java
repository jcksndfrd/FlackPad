package nz.ac.massey.cs.flackpad;

import javax.swing.Action;
import javax.swing.KeyStroke;

public class KeyBinder {
	private TextArea areaInstance;
	
	KeyBinder(Window windowInstance) {
		this.areaInstance = windowInstance.getTextArea();
		addKeyBindings();
	}
	
	private void addKeyBindings() {
		/* This class exists in case we want to add additional keyboard shortcuts, 
		 * different to those with accelerators / mnemonics attached */
    	
		/* Follow this syntax to add a new binding 
		 * 
		 * 1. Define a new action method in the Actions class
		 * 2. Instantiate that action here, as seen below
    	Action myAction = new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Actions.performSave(windowInstance); // This must be a call to the Actions class
			}
    		
    	}; 
    	* 3. Make the key binding
    	makeBinding(areaInstance, ctrl_q, "myAction_msg", myAction);
    	*/ 	
	}
	
	private void makeBinding(TextArea area, KeyStroke key, String msg, Action act) {
		area.getInputMap().put(key, msg);    
        area.getActionMap().put(msg, act);  
	}
}

