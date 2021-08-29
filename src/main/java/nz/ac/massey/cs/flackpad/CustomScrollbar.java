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

	CustomScrollbar(int orientation) {
		super();
		this.orientation = orientation;
	}

	@Override
	protected JButton createDecreaseButton(int orientation) {
		return getButton();
	}

	@Override
	protected JButton createIncreaseButton(int orientation) {
		return getButton();
	}
	
	@SuppressWarnings("serial")
	private JButton getButton() {
		return new JButton() {
			@Override
			public Dimension getPreferredSize() {
				return dimension;
			}
		};
	}

	@Override
	protected void paintTrack(Graphics graphics, JComponent component, Rectangle rectangle) {

	}

	@Override
	protected void paintThumb(Graphics graphics, JComponent component, Rectangle rectangle) {
		final Graphics2D graphics2d = (Graphics2D) graphics.create();
		graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		final JScrollBar scrollbar = (JScrollBar) component;

		Color color;

		if (!scrollbar.isEnabled()) {
			return;
		} else if (rectangle.width > rectangle.height && orientation == 0) {
			return;
		} else if (isDragging) {
			color = Color.decode("#787878");
		} else if (isThumbRollover()) {
			color = Color.decode("#787878");
		} else {
			color = Color.decode("#565656");
		}
		graphics2d.setPaint(color);
		switch (orientation) {
		case 0:
			graphics2d.fillRoundRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height, 20, 10);
			graphics2d.setPaint(Color.BLACK);
			graphics2d.drawRoundRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height, 20, 10);
			break;
		case 1:
			graphics2d.fillRoundRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height, 10, 20);
			graphics2d.setPaint(Color.BLACK);
			graphics2d.drawRoundRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height, 10, 20);
			break;
		}
		graphics2d.dispose();
	}

	@Override
	protected void setThumbBounds(int x, int y, int width, int height) {
		super.setThumbBounds(x, y, width, height);
		scrollbar.repaint();
	}

}
