package nz.ac.massey.cs.flackpad;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.ScrollPaneConstants;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.RTextScrollPane;

@SuppressWarnings("serial")
public class ScrollPane extends RTextScrollPane {

	private static final int scrollBarBreadth = 13;
	
	private final JScrollBar scrollbarVert;
	private final JScrollBar scrollbarHor;
	private final JPanel bottomRightCorner;
	private final JPanel bottomLeftCorner;

	ScrollPane(RSyntaxTextArea textArea) {
		// Call RTextScrollPane constructor with textArea as the contained text area
		super(textArea);

		// Add border
		setBorder(BorderFactory.createEmptyBorder(3, 0, 0, 0));
		setOpaque(false);
		
		// Set up custom scrollbar
		scrollbarVert = getVerticalScrollBar();
		scrollbarHor = getHorizontalScrollBar();
		bottomLeftCorner = new JPanel();
		bottomRightCorner = new JPanel();
		setCustomScrollBar();
		setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
	}

	void setTheme(Config config) {
		// Set font
		getGutter().setLineNumberFont(config.getFont());

		final Color scrollbarColor = config.getTheme().getScrollbarColor();
		scrollbarVert.setBackground(scrollbarColor);
		scrollbarHor.setBackground(scrollbarColor);
		bottomLeftCorner.setBackground(scrollbarColor);
		bottomRightCorner.setBackground(scrollbarColor);
	}

	private void setCustomScrollBar() {
		// Custom Scrollbar
		scrollbarVert.setSize(new Dimension(scrollBarBreadth, Integer.MAX_VALUE));
		scrollbarVert.setMaximumSize(new Dimension(scrollBarBreadth, Integer.MAX_VALUE));
		scrollbarVert.setPreferredSize(new Dimension(scrollBarBreadth, Integer.MAX_VALUE));
		scrollbarVert.setUI(new CustomScrollbar(0));

		// Custom Scrollbar
		scrollbarHor.setSize(new Dimension(Integer.MAX_VALUE, scrollBarBreadth));
		scrollbarHor.setMaximumSize(new Dimension(Integer.MAX_VALUE, scrollBarBreadth));
		scrollbarHor.setPreferredSize(new Dimension(Integer.MAX_VALUE, scrollBarBreadth));
		scrollbarHor.setUI(new CustomScrollbar(1));

		// Set corners
		setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, bottomLeftCorner);
		setCorner(ScrollPaneConstants.LOWER_RIGHT_CORNER, bottomRightCorner);
	}

}
