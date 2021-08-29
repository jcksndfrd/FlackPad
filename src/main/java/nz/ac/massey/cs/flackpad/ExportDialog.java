package nz.ac.massey.cs.flackpad;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
class ExportDialog extends JDialog implements ActionListener {
	
	static final int CLOSED_OPTION = -1;
	static final int EXPORT_OPTION = 0;
	static final int CANCEL_OPTION = 1;
	
	private int option = CLOSED_OPTION;
	
	private final Frame parent;
	private File location;
	
	private File fileChoice;
	private String formatChoice;
	
	JTextField fileField;
	JButton browseButton;
	Choice formatMenu;
	
	JButton cancelButton;
	JButton exportButton;
	
	ExportDialog(Frame parent, File location, String fileName) {
		super(parent, "Export as...", true);
		
		this.parent = parent;
		
		if (location == null) {
			this.location = new File(System.getProperty("user.home") + "/" + fileName);
		} else {
			this.location = location;
		}
		
		setBounds(100, 100, 500, 250);
		
		getContentPane().setLayout(new BorderLayout());
		
		getContentPane().add(getContentPanel(), BorderLayout.CENTER);
		getContentPane().add(getButtonPanel(), BorderLayout.SOUTH);
	}
	
	int showDialog() {
		setVisible(true);
		return option;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		final Object source = event.getSource();
		
		if (source.equals(browseButton)) {
			openBrowseDialog();
		} else if (source.equals(cancelButton)) {
			option = CANCEL_OPTION;
			dispose();
		} else if (source.equals(exportButton)) {
			fileChoice = new File(fileField.getText());
			final int formatLength = formatMenu.getSelectedItem().length();
			formatChoice = formatMenu.getSelectedItem().substring(formatLength - 4, formatLength - 1);
			option = EXPORT_OPTION;
			dispose();
		}
	}
	
	private void openBrowseDialog() {
		final JFileChooser fileChooser = new JFileChooser(location);
		fileChooser.setDialogTitle("Export As");
		
		if (fileChooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
			fileField.setText(fileChooser.getSelectedFile().getAbsolutePath());
		}
	}

	private JPanel getContentPanel() {
		// Create content panel
		final JPanel contentPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		contentPanel.add(getFilePanel());
		contentPanel.add(getFormatPanel());
		
		return contentPanel;
	}

	private JPanel getFilePanel() {
		// Create file panel
		final JPanel filePanel = new JPanel(new BorderLayout());
		filePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		// Add label
		filePanel.add(new JLabel("Destination"), BorderLayout.NORTH);
		
		// Create text field and button panel		
		final JPanel destinationPanel = new JPanel();
		destinationPanel.setLayout(new BoxLayout(destinationPanel, BoxLayout.X_AXIS));
		filePanel.add(destinationPanel, BorderLayout.CENTER);
		
		// Add text field
		fileField = new JTextField(location == null ? null : location.getAbsolutePath(), 37);
		destinationPanel.add(fileField);
		
		// Add browse button
		browseButton = new JButton("...");
		browseButton.setPreferredSize(new Dimension(browseButton.getPreferredSize().width, fileField.getPreferredSize().height));
		browseButton.addActionListener(this);
		destinationPanel.add(browseButton);
		
		return filePanel;
	}

	private JPanel getFormatPanel() {
		// Create format panel
		final JPanel formatPanel = new JPanel(new BorderLayout());
		formatPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		// Add label
		formatPanel.add(new JLabel("Format"), BorderLayout.NORTH);
		
		// Add format choice menu
		formatMenu = new Choice();
		formatPanel.add(formatMenu, BorderLayout.CENTER);
		
		// Populate format choice menu
		formatMenu.add("PDF (.pdf)");
		formatMenu.add("Open Document Text (.odt)");
		formatMenu.add("Rich Text Format (.rtf)");
		
		return formatPanel;
	}

	private JPanel getButtonPanel() {
		// Create button panel
		final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
		
		// Add cancel and export buttons
		cancelButton = new JButton("Cancel");
		exportButton = new JButton("Export");
		
		cancelButton.addActionListener(this);
		exportButton.addActionListener(this);
		
		buttonPanel.add(cancelButton);
		buttonPanel.add(exportButton);
		
		return buttonPanel;
	}
	
	File getFileChoice() {
		return fileChoice;
	}
	
	String getFormatChoice() {
		return formatChoice;
	}
	
}
