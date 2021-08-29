package nz.ac.massey.cs.flackpad;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

class WinListener implements WindowListener {
	
	private final Window window;
	
	WinListener(Window window) {
		this.window = window;
	}

	@Override
	public void windowClosing(WindowEvent event) {
		window.exit();
	}

	@Override
	public void windowOpened(WindowEvent event) {
		// Ignore
	}

	@Override
	public void windowClosed(WindowEvent event) {
		// Ignore
	}

	@Override
	public void windowIconified(WindowEvent event) {
		// Ignore
	}

	@Override
	public void windowDeiconified(WindowEvent event) {
		// Ignore
	}

	@Override
	public void windowActivated(WindowEvent event) {
		// Ignore
	}

	@Override
	public void windowDeactivated(WindowEvent event) {
		// Ignore
	}

}
