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

final class DialogAboutUtils extends MouseAdapter {
	
	//GitHub URLs
	private static final String JACK_URL = "https://github.com/jcksndfrd";
	private static final String FLETCH_URL = "https://github.com/fletchthefletch";
	
	// Colours
	private static final Color LINK_COLOUR = Color.decode("#008787");
	private static final Color HOVER_COLOUR = Color.decode("#009900");
	
	private final Component parent;
	private final JLabel jack;
	private final JLabel fletch;
	
	private DialogAboutUtils(Component parent) {
		super();
		
		this.parent = parent;
		
	    // Jack's link
	    jack = new JLabel("Jack Sandford");
        jack.addMouseListener(this);
	    jack.setForeground(LINK_COLOUR);
        
	    // Fletch's link
	    fletch = new JLabel("Fletcher van Ameringen");
	    fletch.addMouseListener(this);	 
	    fletch.setForeground(LINK_COLOUR);
	}
	
	static void show(Component parent, ImageIcon icon) {
		final DialogAboutUtils links = new DialogAboutUtils(parent);
		
	    final JPanel jPanel = new JPanel();
	    jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
	    final JPanel jPanelFooting = new JPanel();
	    
	    
	    final JLabel mainMessageA = new JLabel("This application was created for a tertiary assignment in late 2021."); 
	    final JLabel mainMessageB = new JLabel("We hope you enjoy using it as much as we enjoyed making it!");
	    
	    jPanel.add(mainMessageA);	
	    jPanel.add(mainMessageB);	
	    jPanel.setAlignmentX(SwingConstants.CENTER);
	    jPanel.setAlignmentY(SwingConstants.CENTER);
	    
	    jPanelFooting.add(new JLabel("-"));
	    jPanelFooting.add(links.fletch);
	    jPanelFooting.add(new JLabel("&"));
	    jPanelFooting.add(links.jack);
	    jPanelFooting.setAlignmentX(SwingConstants.CENTER);
	    jPanelFooting.setAlignmentY(SwingConstants.CENTER);
	    
	    jPanel.add(jPanelFooting);
	    
		JOptionPane.showMessageDialog(parent, jPanel, "About FlackPad", JOptionPane.INFORMATION_MESSAGE, icon);
	}
	
	@Override
    public void mouseClicked(MouseEvent event) {
		final JLabel source = (JLabel) event.getSource();
		
        try {
            Desktop.getDesktop().browse(new URI(source.equals(fletch) ? FLETCH_URL : JACK_URL));
        } catch (IOException | URISyntaxException error) {
			DialogUtils.warning("Could not open URL", parent);
        }
    }
	
    @Override
    public void mouseEntered(MouseEvent event) {
		final JLabel source = (JLabel) event.getSource();
    	source.setForeground(HOVER_COLOUR);
    }
    
    @Override
    public void mouseExited(MouseEvent event) {
		final JLabel source = (JLabel) event.getSource();
    	source.setForeground(LINK_COLOUR);
    } 
}
