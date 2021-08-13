package nz.ac.massey.cs.flackpad;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
class ThemeDialog extends JDialog {

	private JTextField fontTextField;
	private JList fontList;
	
	private JTextField StyleTextField;
	private JList styleList;
	
	private JTextField sizeTextField;
	private JList sizeList;

	ThemeDialog(Frame parent) {
		super(parent, "Font and Theme Preferences", true);
		setBounds(100, 100, 450, 300);

		getContentPane().setLayout(new BorderLayout());

		getContentPane().add(getContentPanel(), BorderLayout.CENTER);
		getContentPane().add(getButtonPanel(), BorderLayout.SOUTH);
	}

	private JPanel getContentPanel() {
		// Create content panel
		JPanel contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(new BorderLayout(0, 0));

		// Font panel
		JPanel mainFontPanel = new JPanel();

		// Font family panel
		JPanel fontPanel = new JPanel();
		fontPanel.setLayout(new BorderLayout(0, 0));
		mainFontPanel.add(fontPanel);

		fontPanel.add(new JLabel("Font:"), BorderLayout.NORTH);

		fontTextField = new JTextField();
		fontTextField.setColumns(10);
		fontPanel.add(fontTextField, BorderLayout.CENTER);

		fontList = new JList();
		fontPanel.add(fontList, BorderLayout.SOUTH);

		// Font style panel
		JPanel stylePanel = new JPanel();
		stylePanel.setLayout(new BorderLayout(0, 0));
		mainFontPanel.add(stylePanel);

		JLabel styleLabel = new JLabel("Style:");
		stylePanel.add(styleLabel, BorderLayout.NORTH);

		StyleTextField = new JTextField();
		StyleTextField.setColumns(10);
		stylePanel.add(StyleTextField, BorderLayout.CENTER);

		styleList = new JList();
		stylePanel.add(styleList, BorderLayout.SOUTH);

		// Font size panel
		JPanel sizePanel = new JPanel();
		sizePanel.setLayout(new BorderLayout(0, 0));
		mainFontPanel.add(sizePanel);

		JLabel sizeLabel = new JLabel("Size:");
		sizePanel.add(sizeLabel, BorderLayout.NORTH);

		sizeTextField = new JTextField();
		sizePanel.add(sizeTextField, BorderLayout.CENTER);
		sizeTextField.setColumns(10);

		sizeList = new JList();
		sizePanel.add(sizeList, BorderLayout.SOUTH);

		// Theme panel
		JPanel mainThemePanel = new JPanel();
		mainThemePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JPanel themePanel = new JPanel();
		themePanel.setLayout(new BorderLayout(0, 0));
		mainThemePanel.add(themePanel);

		JLabel themeLabel = new JLabel("Theme:");
		themePanel.add(themeLabel, BorderLayout.NORTH);

		Choice themeChoice = new Choice();
		themePanel.add(themeChoice);

		// Add font and theme panels
		contentPanel.add(mainFontPanel, BorderLayout.WEST);
		contentPanel.add(mainThemePanel, BorderLayout.EAST);

		return contentPanel;
	}

	private Component getButtonPanel() {
		// Create button panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout(0, 0));
		
		// Set defaults panel
		JPanel defaultPanel = new JPanel();
		
		JButton defaultButton = new JButton("Load Defaults");
		defaultButton.setHorizontalAlignment(SwingConstants.LEFT);
		defaultButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		defaultPanel.add(defaultButton);

		// Save and cancel panel
		JPanel savePanel = new JPanel();

		JButton saveButton = new JButton("Save");
		saveButton.setActionCommand("Save");
		savePanel.add(saveButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Cancel");
		savePanel.add(cancelButton);
		
		// Add set defaults and save panels
		buttonPanel.add(defaultPanel, BorderLayout.WEST);
		buttonPanel.add(savePanel, BorderLayout.EAST);
		
		return buttonPanel;
	}

}
