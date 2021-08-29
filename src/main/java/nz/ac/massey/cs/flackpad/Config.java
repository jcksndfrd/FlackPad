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

final class Config {

	private final Map<String, Object> defaultsMap = new LinkedHashMap<>();
	private Map<String, Object> configMap = new LinkedHashMap<>();

	private final Component parent;
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
		defaultsMap.put("fontFamily", "Consolas");
		defaultsMap.put("fontStyle", Font.PLAIN);
		defaultsMap.put("fontSize", 12);
		defaultsMap.put("theme", "light");
	}

	private void loadConfigFile() {
		// Read config file
		final Yaml yaml = new Yaml();

		try (InputStream inputStream = new FileInputStream("config.yaml")) {
			configMap = yaml.load(inputStream);
		} catch (FileNotFoundException e) {
			DialogUtils.message("Configuration file does not exist, using defaults", parent);
		} catch (IOException e) {
			DialogUtils.error("Something went wrong when loading configuration file", parent);
		}

		// Set missing values with defaults
		for (final String key : defaultsMap.keySet()) {
			if (!configMap.containsKey(key)) {
				configMap.put(key, defaultsMap.get(key));
			}
		}

		// Get Theme object from config map theme value
		setFontFromMap();
		setThemeFromMap();
	}

	private void setFontFromMap() {
		// Get font values
		final Object fontFamily = configMap.get("fontFamily");
		final Object fontStyle = configMap.get("fontStyle");
		final Object fontSize = configMap.get("fontSize");

		// Check font values are valid and set font
		if (fontFamily.getClass() == String.class && fontStyle.getClass() == Integer.class
				&& fontSize.getClass() == Integer.class) {
			font = new Font((String) fontFamily, (Integer) fontStyle, (Integer) fontSize);
		} else {
			// Use default font
			setFont(new Font((String) defaultsMap.get("fontFamily"), (int) defaultsMap.get("fontStyle"),
					(int) defaultsMap.get("fontSize")));
			DialogUtils.error("Something went wrong when loading the configured font, using default font.", parent);
		}
	}

	private void setThemeFromMap() {
		// Get theme value
		final Object themeName = configMap.get("theme");
		// Set theme
		if ("light".equals(themeName)) {
			theme = new MainTheme("light");
		} else if ("dark".equals(themeName)) {
			theme = new MainTheme("dark");
		} else {
			// Use default theme
			configMap.put("theme", defaultsMap.get("theme"));
			theme = new MainTheme((String) configMap.get("theme"));
			DialogUtils.error("Something went wrong when loading the configured theme, using default theme \""
					+ (String) configMap.get("theme") + "\".", parent);
		}
	}

	void saveConfigFile() {
		// Get Yaml instance with format options
		final DumperOptions options = new DumperOptions();
		options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
		options.setPrettyFlow(true);
		final Yaml yaml = new Yaml(options);

		// Write config map to yaml file
		try (PrintWriter writer = new PrintWriter("config.yaml")) {
			yaml.dump(configMap, writer);
			writer.flush();
		} catch (IOException e) {
			DialogUtils.error("Something went wrong when saving configuration", parent);
		}
	}

	Font getFont() {
		// return new Font instance with the same values as saved font
		return font.deriveFont(font.getStyle(), font.getSize());
	}

	void setFont(Font font) {
		// Set font values
		configMap.put("fontFamily", font.getFamily());
		configMap.put("fontStyle", font.getStyle());
		configMap.put("fontSize", font.getSize());
		setFontFromMap();
	}

	MainTheme getTheme() {
		return theme;
	}

	String getThemeName() {
		return (String) configMap.get("theme");
	}

	void setTheme(String themeName) {
		// Check themeName is valid
		if ("light".equals(themeName) || "dark".equals(themeName)) {
			configMap.put("theme", themeName);
			setThemeFromMap();
		} else {
			DialogUtils.error("Theme \"" + themeName + "\" is not a valid theme", parent);
		}
	}

	Font getDefaultFont() {
		return new Font((String) defaultsMap.get("fontFamily"), (Integer) defaultsMap.get("fontStyle"),
				(Integer) defaultsMap.get("fontSize"));
	}

	String getDefaultThemeName() {
		return (String) defaultsMap.get("theme");
	}

}
