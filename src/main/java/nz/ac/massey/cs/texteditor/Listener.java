package nz.ac.massey.cs.texteditor;

import java.awt.event.*;

public class Listener implements ActionListener {

	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getActionCommand());
	}

}
