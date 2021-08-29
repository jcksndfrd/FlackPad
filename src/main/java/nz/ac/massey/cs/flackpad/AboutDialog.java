package nz.ac.massey.cs.flackpad;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

final class AboutDialog {
	
	private AboutDialog() {
		throw new UnsupportedOperationException();
	}
	
	static void show(Component parent, ImageIcon icon) {
	    final JPanel jPanel = new JPanel();
	    jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
	    final JPanel jPanelFooting = new JPanel();
	    
	    // Jack's link
	    final JLabel jack = new JLabel("Jack Sandford");
        jack.addMouseListener(new MouseAdapter() {
        	 
            @Override
            public void mouseClicked(MouseEvent event) {
                try {
                    Desktop.getDesktop().browse(new URI("https://github.com/jcksndfrd"));
                } catch (IOException | URISyntaxException error) {
        			System.out.println("Dialogs\\About() - Error: Could not open URL");
                }
            } 
            @Override
            public void mouseEntered(MouseEvent event) {
            	jack.setForeground(Color.decode("#009900"));
            } 
            @Override
            public void mouseExited(MouseEvent event) {
            	jack.setForeground(Color.decode("#008787"));
            } 
        });
        
	    // Fletch's link
	    final JLabel fletch = new JLabel("Fletcher van Ameringen");
	    fletch.addMouseListener(new MouseAdapter() {
        	 
            @Override
            public void mouseClicked(MouseEvent event) {
                try {
                    Desktop.getDesktop().browse(new URI("https://github.com/fletchthefletch"));
                } catch (IOException | URISyntaxException error) {
        			System.out.println("Dialogs\\About() - Error: Could not open URL");
                }
            } 
            @Override
            public void mouseEntered(MouseEvent event) {
                fletch.setForeground(Color.decode("#009900"));
            } 
            @Override
            public void mouseExited(MouseEvent event) {
                fletch.setForeground(Color.decode("#008787"));
            } 
        });	 
	    fletch.setForeground(Color.decode("#008787"));
	    jack.setForeground(Color.decode("#008787"));
	    
	    final JLabel mainMessageA = new JLabel("This application was created for a tertiary assignment in late 2021."); 
	    final JLabel mainMessageB = new JLabel("We hope you enjoy using it at much as we enjoyed making it!");
	    
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
