package nz.ac.massey.cs.flackpad;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class CustomScrollbar extends BasicScrollBarUI {
	
	  private final Dimension dimension = new Dimension(0, 0);
	  private final int orientation; // default to vertical

	  CustomScrollbar(int o) {
		  orientation = o;
	  }
	  
	  @Override
	  protected JButton createDecreaseButton(int orientation) {
	    return new JButton() {
			private static final long serialVersionUID = 1L;

		@Override
	      public Dimension getPreferredSize() {
	        return dimension;
	      }
	    };
	  }

	  @Override
	  protected JButton createIncreaseButton(int orientation) {
	    return new JButton() {
			private static final long serialVersionUID = 1L;

		@Override
	      public Dimension getPreferredSize() {
	        return dimension;
	      }
	    };
	  }

	  @Override
	  protected void paintTrack(Graphics g, JComponent c, Rectangle r) {
		    
	  }

	  @Override
	  protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
	    Graphics2D g2 = (Graphics2D) g.create();
	    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    
	    JScrollBar scrollbar = (JScrollBar) c;
	    
	    Color color;

	    if (!scrollbar.isEnabled()) {
	    	return;
	    }
	    else if (r.width > r.height && orientation == 0) {
	      return;
	    }
	    else if (isDragging) {
	      color = Color.decode("#787878");
	    } else if (isThumbRollover()) {
	      color = Color.decode("#787878");
	    } else {
	      color = Color.decode("#565656");
	    }
	    g2.setPaint(color);	    
	    switch(orientation) {
	    case 0:
		    g2.fillRoundRect(r.x, r.y, r.width, r.height, 20, 10);
		    g2.setPaint(Color.BLACK);
		    g2.drawRoundRect(r.x, r.y, r.width, r.height, 20, 10);
		    break;
	    case 1:
		    g2.fillRoundRect(r.x, r.y, r.width, r.height, 10, 20);
		    g2.setPaint(Color.BLACK);
		    g2.drawRoundRect(r.x, r.y, r.width, r.height, 10, 20);
		    break;
	    }
	    g2.dispose();
	  }

	  @Override
	  protected void setThumbBounds(int x, int y, int width, int height) {
	    super.setThumbBounds(x, y, width, height);
	    scrollbar.repaint();
	  }

}
