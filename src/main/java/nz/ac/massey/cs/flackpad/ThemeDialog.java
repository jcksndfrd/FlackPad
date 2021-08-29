package nz.ac.massey.cs.flackpad;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualLinkedHashBidiMap;
import org.apache.commons.text.WordUtils;

@SuppressWarnings("serial")
class ThemeDialog extends JDialog implements ActionListener {

	static final int CLOSED_OPTION = -1;
	static final int SAVE_OPTION = 0;
	static final int CANCEL_OPTION = 1;

	private int option;

	private Font fontChoice;
	private final Font defaultFont;
	private String themeChoice;
	private final String defaultTheme;

	private JList<String> fontList;
	private JList<String> styleList;
	private JSpinner sizeSpinner;
	private Choice themeMenu;

	private BidiMap<Integer, String> styleMap;

	ThemeDialog(Frame parent, Font currentFont, String currentTheme, Font defaultFont, String defaultTheme) {
		super(parent, "Font and Theme Preferences", true);

		setStyleMap();

		fontChoice = currentFont;
		themeChoice = currentTheme;

		this.defaultFont = defaultFont;
		this.defaultTheme = defaultTheme;

		setBounds(100, 100, 450, 300);

		getContentPane().setLayout(new BorderLayout());

		getContentPane().add(getContentPanel(), BorderLayout.CENTER);
		getContentPane().add(getButtonPanel(), BorderLayout.SOUTH);

		updateSelectedOptions();
	}

	int showDialog() {
		setVisible(true);
		return option;
	}

	@Override
	public void actionPerformed(ActionEvent e) {		
		switch (e.getActionCommand()) {
		case "Load Defaults":
			fontChoice = defaultFont;
			themeChoice = defaultTheme;
			updateSelectedOptions();
			break;
		case "Save":
			fontChoice = new Font(fontList.getSelectedValue(), styleMap.getKey(styleList.getSelectedValue()), (int) sizeSpinner.getValue());
			themeChoice = WordUtils.uncapitalize(themeMenu.getSelectedItem());
			option = SAVE_OPTION;
			dispose();
			break;
		case "Cancel":
			option = CANCEL_OPTION;
			dispose();
			break;
		}
	}

	private void updateSelectedOptions() {
		// Set selected options
		fontList.setSelectedValue(fontChoice.getFamily(), true);
		styleList.setSelectedValue(styleMap.get(fontChoice.getStyle()), true);
		sizeSpinner.setValue(fontChoice.getSize());
		themeMenu.select(WordUtils.capitalize(themeChoice));
	}

	private JPanel getContentPanel() {
		// Create content panel
		JPanel contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));

		// Add option panels
		contentPanel.add(getFontFamilyPanel());
		contentPanel.add(getFontStylePanel());
		contentPanel.add(getFontSizePanel());
		contentPanel.add(getThemePanel());
		
		return contentPanel;
	}

	private JPanel getFontFamilyPanel() {
		// Create font family panel
		JPanel fontPanel = new JPanel(new BorderLayout(0, 5));
		fontPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		// Add label
		JLabel fontLabel = new JLabel("Font:");
		fontPanel.add(fontLabel, BorderLayout.NORTH);

		// Add list
		fontList = new JList<String>();
		fontPanel.add(new JScrollPane(fontList), BorderLayout.CENTER);

		// Populate list
		fontList.setListData(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());

		return fontPanel;
	}

	private JPanel getFontStylePanel() {
		// Create font style panel
		JPanel stylePanel = new JPanel(new BorderLayout(0, 5));
		stylePanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		// Add label
		JLabel styleLabel = new JLabel("Style:");
		stylePanel.add(styleLabel, BorderLayout.NORTH);

		// Add list
		JPanel content = new JPanel(new BorderLayout(0, 0));
		styleList = new JList<String>();
		content.add(styleList, BorderLayout.NORTH);
		stylePanel.add(content, BorderLayout.CENTER);

		// Populate list
		styleList.setListData(styleMap.values().toArray(new String[4]));

		return stylePanel;
	}

	private JPanel getFontSizePanel() {
		// Create font size panel
		JPanel sizePanel = new JPanel(new BorderLayout(0, 5));
		sizePanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		// Add label
		JLabel sizeLabel = new JLabel("Size:");
		sizePanel.add(sizeLabel, BorderLayout.NORTH);

		// Add spinner
		JPanel content = new JPanel(new BorderLayout(0, 0));
		sizeSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 72, 1));
		content.add(sizeSpinner, BorderLayout.NORTH);
		sizePanel.add(content, BorderLayout.CENTER);

		return sizePanel;
	}

	private JPanel getThemePanel() {
		// Create theme panel
		JPanel themePanel = new JPanel(new BorderLayout(0, 5));
		themePanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		// Add label
		JLabel themeLabel = new JLabel("Theme:");
		themePanel.add(themeLabel, BorderLayout.NORTH);
		
		// Add choice menu
		JPanel content = new JPanel(new BorderLayout(0, 0));
		themeMenu = new Choice();
		content.add(themeMenu, BorderLayout.NORTH);
		themePanel.add(content, BorderLayout.CENTER);
		
		// Populate choice menu
		themeMenu.add("Light");
		themeMenu.add("Dark");

		return themePanel;
	}

	private JPanel getButtonPanel() {
		// Create button panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout(0, 0));

		// Set defaults panel
		JPanel defaultPanel = new JPanel();

		JButton defaultButton = new JButton("Load Defaults");
		defaultButton.addActionListener(this);
		defaultPanel.add(defaultButton);

		// Save and cancel panel
		JPanel savePanel = new JPanel();

		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(this);
		savePanel.add(saveButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		savePanel.add(cancelButton);

		// Add set defaults and save panels
		buttonPanel.add(defaultPanel, BorderLayout.WEST);
		buttonPanel.add(savePanel, BorderLayout.EAST);

		return buttonPanel;
	}

	private void setStyleMap() {
		styleMap = new DualLinkedHashBidiMap<Integer, String>();
		styleMap.put(Font.PLAIN, "Plain");
		styleMap.put(Font.BOLD, "Bold");
		styleMap.put(Font.ITALIC, "Italic");
		styleMap.put(Font.BOLD | Font.ITALIC, "Bold and Italic");
	}
	
	Font getFontChoice() {
		return fontChoice;
	}
	
	String getThemeChoice() {
		return themeChoice;
	}

}
