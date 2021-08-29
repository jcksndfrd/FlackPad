package nz.ac.massey.cs.flackpad;

import java.awt.Color;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JMenuBar;

public class InformationBar {

	private JLabel details;
	private JLabel zoomlevel;
	private JLabel filetype;
	
	public InformationBar(JMenuBar menu) {
		// Char count
		details = new JLabel("0 | Char");
		details.setBorder(
				BorderFactory.createCompoundBorder(menu.getBorder(), BorderFactory.createEmptyBorder(0, 0, 0, 10)));
		menu.add(Box.createHorizontalGlue());
		menu.add(details);

		// Zoom percentage
		zoomlevel = new JLabel();
		zoomlevel.setBorder(
				BorderFactory.createCompoundBorder(menu.getBorder(), BorderFactory.createEmptyBorder(0, 0, 0, 10)));
		zoomlevel.setForeground(Color.decode("#444444"));
		menu.add(zoomlevel);

		// File type label
		filetype = new JLabel("PLAIN");
		filetype.setBorder(
				BorderFactory.createCompoundBorder(menu.getBorder(), BorderFactory.createEmptyBorder(0, 0, 0, 10)));
		filetype.setForeground(Color.decode("#777777"));
		menu.add(filetype);
	}
	void setTheme(Config config) {
		Color theme = "light".equals(config.getThemeName()) ? Color.decode("#990000") : Color.decode("#770BD8");
		details.setForeground(theme);
	}

	void setInformationBarText(String val) {
		details.setText(val);
	}

	void setInformationBarFileText(String val) {
		if (val.startsWith("text/")) {
			filetype.setText(val.substring(5).toUpperCase(Locale.US));
		} else {
			filetype.setText(val);
		}
	}

	void setInformationBarZoomText(String val) {
		zoomlevel.setText(val);
	}

	void setInformationBarZoomVisible(boolean isVisible) {
		zoomlevel.setVisible(isVisible);
	}

}
