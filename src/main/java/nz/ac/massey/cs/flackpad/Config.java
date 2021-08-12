package nz.ac.massey.cs.flackpad;

import java.awt.Component;
import java.awt.Font;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

class Config {

	private Map<String, Object> defaults = new LinkedHashMap<String, Object>();
	private Map<String, Object> config = new LinkedHashMap<String, Object>();

	private Component parent;
	private Font font;
	private MainTheme theme;

	Config(Component parent) {
		// Save parent component to instance
		this.parent = parent;

		// Load default values and config file
		loadDefaults();
		loadConfigFile();
	}

	private void loadDefaults() {
		// Default values
		defaults.put("fontFamily", "Dialog");
		defaults.put("fontStyle", Font.PLAIN);
		defaults.put("fontSize", 12);
		defaults.put("theme", "light");
	}

	void loadConfigFile() {
		// Read config file
		Yaml yaml = new Yaml();

		try {
			InputStream inputStream = new FileInputStream("config.yaml");
			config = yaml.load(inputStream);
			inputStream.close();
		} catch (FileNotFoundException e) {
			Dialogs.error("Configuration file does not exist, using defaults", parent);
		} catch (IOException e) {
			Dialogs.error("Something went wrong when loading configuration file", parent);
		}

		// Set missing values with defaults
		for (String key : defaults.keySet()) {
			if (!config.containsKey(key))
				config.put(key, defaults.get(key));
		}

		// Get Theme object from config theme value
		getFontFromConfig();
		getThemeFromConfig();
	}

	private void getFontFromConfig() {
		// Get font values
		Object fontFamily = config.get("fontFamily");
		Object fontStyle = config.get("fontStyle");
		Object fontSize = config.get("fontSize");

		// Check font values are valid
		if (fontFamily.getClass() == String.class && fontStyle.getClass() == Integer.class
				&& fontSize.getClass() == Integer.class) {
			font = new Font((String) fontFamily, (Integer) fontStyle, (Integer) fontSize);
		} else {
			// Use default font
			setFont(new Font((String) defaults.get("fontFamily"), (int) defaults.get("fontStyle"),
					(int) defaults.get("fontSize")));
			Dialogs.error("Something went wrong when loading the configured font, using default font.", parent);
		}
	}

	private void getThemeFromConfig() {
		// Get theme value
		Object themeValue = config.get("theme");

		// Check theme value is a string
		if (themeValue.getClass() == String.class) {
			String themeName = (String) themeValue;
			// Check theme value is valid
			if (themeName.contentEquals("light")) {
				theme = new MainTheme("light");
				return;
			} else if (themeName.contentEquals("dark")) {
				theme = new MainTheme("dark");
				return;
			}
		}
		
		// Use default theme
		config.put("theme", defaults.get("theme"));
		theme = new MainTheme((String) config.get("theme"));
		Dialogs.error("Something went wrong when loading the configured theme, using default theme \""
				+ (String) config.get("theme") + "\".", parent);
	}

	void saveConfigFile() {
		// Get Yaml instance with format options
		DumperOptions options = new DumperOptions();
		options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
		options.setPrettyFlow(true);
		Yaml yaml = new Yaml(options);

		// Write config map to yaml file
		try {
			PrintWriter writer = new PrintWriter("config.yaml");
			yaml.dump(config, writer);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			Dialogs.error("Something went wrong when saving configuration", parent);
		}
	}

	Font getFont() {
		// return new Font instance with the same values as saved font
		return font.deriveFont(font.getSize());
	}

	void setFont(Font font) {
		// Set font values
		config.put("fontFamily", font.getFamily());
		config.put("fontStyle", font.getStyle());
		config.put("fontSize", font.getSize());
		getFontFromConfig();
	}

	MainTheme getTheme() {
		return theme;
	}
	
	void setTheme(String themeName) {
		// Check themeName is valid
		if (themeName == "dark" || themeName == "light") {
			config.put("theme", themeName);
			getThemeFromConfig();
		} else {
			Dialogs.error("Theme \"" + themeName + "\" is not a valid theme", parent);
		}
	}

}
