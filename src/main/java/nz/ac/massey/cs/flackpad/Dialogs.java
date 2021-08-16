package nz.ac.massey.cs.flackpad;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

final class Dialogs {
	
	private Dialogs() {
		throw new UnsupportedOperationException();
	}

	public static void message(String message, Component parent) {
		JOptionPane.showMessageDialog(parent, message, "Info", JOptionPane.INFORMATION_MESSAGE);
	}
	
	static void error(String message, Component parent) {
		JOptionPane.showMessageDialog(parent, message, "Error", JOptionPane.WARNING_MESSAGE);
	}
	
	static void warning(String message, Component parent) {
		JOptionPane.showMessageDialog(parent, message, "Warning", JOptionPane.WARNING_MESSAGE);
	}
	
	static int saveWarning(String fileName, Component parent) {
		return JOptionPane.showOptionDialog(parent, "Do you want to save changes to " + fileName,
				"Warning: Unsaved Changes", 0, JOptionPane.PLAIN_MESSAGE, null, new String[] { "Save", "Don't Save", "Cancel" }, null);
	}
	
	static void about(Component parent, ImageIcon icon) {
	    JPanel jPanel = new JPanel();
	    jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
	    JPanel jPanelFooting = new JPanel();
	    
	    // Jack's link
	    JLabel jack = new JLabel("Jack Sandford");
        jack.addMouseListener(new MouseAdapter() {
        	 
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://github.com/jcksndfrd"));
                } catch (IOException | URISyntaxException e1) {
                    e1.printStackTrace();
                }
            } 
            @Override
            public void mouseEntered(MouseEvent e) {
            	jack.setForeground(Color.decode("#009900"));
            } 
            @Override
            public void mouseExited(MouseEvent e) {
            	jack.setForeground(Color.decode("#008787"));
            } 
        });
        
	    // Fletch's link
	    JLabel fletch = new JLabel("Fletcher van Ameringen");
	    fletch.addMouseListener(new MouseAdapter() {
        	 
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://github.com/fletchthefletch"));
                } catch (IOException | URISyntaxException e1) {
                    e1.printStackTrace();
                }
            } 
            @Override
            public void mouseEntered(MouseEvent e) {
                fletch.setForeground(Color.decode("#009900"));
            } 
            @Override
            public void mouseExited(MouseEvent e) {
                fletch.setForeground(Color.decode("#008787"));
            } 
        });	 
	    fletch.setForeground(Color.decode("#008787"));
	    jack.setForeground(Color.decode("#008787"));
	    
	    JLabel mainMessageA = new JLabel("This application was created for a tertiary assignment in late 2021."); 
	    JLabel mainMessageB = new JLabel("We hope you enjoy using it at much as we enjoyed making it!");
	    
	    jPanel.add(mainMessageA);	
	    jPanel.add(mainMessageB);	
	    jPanel.setAlignmentX(SwingConstants.CENTER);
	    jPanel.setAlignmentY(SwingConstants.CENTER);
	    
	    jPanelFooting.add(new JLabel("-"));
	    jPanelFooting.add(fletch);
	    jPanelFooting.add(new JLabel("&"));
	    jPanelFooting.add(jack);
	    jPanelFooting.setAlignmentX(SwingConstants.CENTER);
	    jPanelFooting.setAlignmentY(SwingConstants.CENTER);
	    
	    jPanel.add(jPanelFooting);
	    
		JOptionPane.showMessageDialog(parent, jPanel, "About FlackPad", JOptionPane.INFORMATION_MESSAGE, icon);
	}
}
