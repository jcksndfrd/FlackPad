package nz.ac.massey.cs.flackpad;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Element;

@SuppressWarnings("serial")
class Window extends JFrame {
	private String name = "FlackPad";
	private JFrame frame;
	private WindowListener winListener;
	private Config config;
	private FileMIME MIME = new FileMIME();

	private MenuBar menuBar;
	private TextArea textArea;
	private TextArea lines;
	private JScrollPane scrollPane;

	private boolean saved = true;
	private File file;
	private String fileName;

	Window() {
		// Create JFrame and set title
		fileName = "Untitled";
		frame = new JFrame(fileName + " - " + name);

		// Add icon
		frame.setIconImages(
				List.of(new ImageIcon("icons/16x16.png").getImage(), new ImageIcon("icons/32x32.png").getImage(),
						new ImageIcon("icons/64x64.png").getImage(), new ImageIcon("icons/128x128.png").getImage()));

		// Get config
		config = new Config(this);

		// Add window listener
		winListener = new WinListener(this);
		frame.addWindowListener(winListener);

		// Add menu bar
		menuBar = new MenuBar(this);
		frame.add(menuBar);
		frame.setJMenuBar(menuBar);

		// Add text area in a scroll pane
		textArea = new TextArea(this, config);

		scrollPane = new JScrollPane();

		// Add lines to text area
		lines = new TextArea(this, config, "1");
		lines.setBackground(Color.LIGHT_GRAY);
		lines.setEditable(false);
		lines.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

		Color linesBackgroundColorHover = Color.decode("#222222");
		Color linesBackgroundColor = Color.decode("#383838");

		lines.setBorder(
				BorderFactory.createCompoundBorder(lines.getBorder(), BorderFactory.createEmptyBorder(0, 5, 0, 5)));
		lines.setBackground(linesBackgroundColor); // change to get from config
		lines.setForeground(textArea.getSelectionColor()); // change to get from config

		lines.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				scrollPane.getRowHeader().setVisible(false);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				lines.setBackground(linesBackgroundColorHover); // change to get from config
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lines.setBackground(linesBackgroundColor); // change to get from config
			}

		});

		// Add listener
		textArea.getDocument().addDocumentListener(new DocumentListener() {
			public String getText() {
				int caretPosition = textArea.getDocument().getLength();
				Element root = textArea.getDocument().getDefaultRootElement();
				String text = "1" + System.getProperty("line.separator");
				for (int i = 2; i < root.getElementIndex(caretPosition) + 2; i++) {
					text += i + System.getProperty("line.separator");
				}
				return text;
			}

			@Override
			public void changedUpdate(DocumentEvent de) {
				lines.setText(getText());
			}

			@Override
			public void insertUpdate(DocumentEvent de) {
				lines.setText(getText());
			}

			@Override
			public void removeUpdate(DocumentEvent de) {
				lines.setText(getText());
			}
		});

		scrollPane.getViewport().add(textArea);
		scrollPane.setRowHeaderView(lines);
		scrollPane.setBorder(null);

		frame.add(scrollPane);

		// Add key bindings to instance
		new KeyBinder(this);

		// Set window size, visibility and to not close
		frame.setSize(1000, 500);
		frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		frame.setVisible(true);

		frame.requestFocus();
		textArea.grabFocus();

	}

	void newDoc() {
		int saveChoice = this.isSaved() ? 1 : Dialogs.saveWarning(fileName, this);
		int saved = FileIO.SAVED;

		if (saveChoice == JOptionPane.CANCEL_OPTION || saveChoice == JOptionPane.CLOSED_OPTION)
			return;
		if (saveChoice == JOptionPane.YES_OPTION)
			saved = FileIO.save(this);

		if (saved == FileIO.SAVED) {
			this.setFile(null);
			this.textArea.setText("");
			this.setSaved(true);
		}
	}

	void exit() {
		int saveChoice = this.isSaved() ? 1 : Dialogs.saveWarning(fileName, this);
		int saved = FileIO.SAVED;

		if (saveChoice == JOptionPane.CANCEL_OPTION || saveChoice == JOptionPane.CLOSED_OPTION)
			return;
		if (saveChoice == JOptionPane.YES_OPTION)
			saved = FileIO.save(this);

		if (saved == FileIO.SAVED) {
			config.saveConfigFile();
			frame.dispose();
		}
	}

	void setInformationBar(String val) {
		menuBar.setInformationBarText(val);;
	}
	JScrollPane getLineScrollPane() {
		return scrollPane;
	}

	JTextArea getLineScrollTextArea() {
		return lines;
	}

	JTextField getFindField() {
		return menuBar.getFindField();
	}

	JButton getFindClose() {
		return menuBar.getFindClose();
	}

	void showFindBar() {
		menuBar.showFindBar();
	}

	void hideFindBar() {
		menuBar.hideFindBar();
	}

	JFrame getFrame() {
		return frame;
	}

	TextArea getTextArea() {
		return textArea;
	}
	TextArea getLines() {
		return lines;
	}

	boolean isSaved() {
		return saved;
	}

	void setSaved(boolean saved) {
		frame.setTitle((saved ? "" : "*") + fileName + " - " + name);
		this.saved = saved;
	}

	File getFile() {
		return file;
	}

	void setFile(File file) {
		this.file = file;
		fileName = file == null ? "Untitled" : file.getName();
		textArea.setSyntaxEditingStyle(MIME.getFileStyle(file));
	}

	String getFileName() {
		return fileName;
	}

	String getAppName() {
		return name;
	}

	void setText(String text) {
		textArea.setText(text);
	}
}
