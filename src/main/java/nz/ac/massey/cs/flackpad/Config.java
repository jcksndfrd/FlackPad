package nz.ac.massey.cs.flackpad;

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
	
	private Window window;
	private Map<String, Object> defaults = new LinkedHashMap<String, Object>();
	private Map<String, Object> config = new LinkedHashMap<String, Object>();
	
	Config(Window window) {
		this.window = window;
		
		loadDefaults();
		loadConfigFile();
	}
	
	private void loadDefaults() {
		defaults.put("fontFamily", "Dialog");
		defaults.put("fontStyle", Font.PLAIN);
		defaults.put("fontSize", 12);
	}
	
	void loadConfigFile() {
		// Read config file
		Yaml yaml = new Yaml();
		
		try {
			InputStream inputStream = new FileInputStream("config.yaml");
			config = yaml.load(inputStream);
			inputStream.close();
		} catch (FileNotFoundException e) {
			Dialogs.error("Configuration file does not exist, using defaults", window);
		} catch (IOException e) {
			Dialogs.error("Something wen't wrong when loading configuration file", window);
		}

		// Set missing values with defaults
		for (String key:defaults.keySet()) {
			if (!config.containsKey(key)) config.put(key, defaults.get(key));
		}
		
		// Save config
		saveConfigFile();
	}

	void saveConfigFile() {
		DumperOptions options = new DumperOptions();
		options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
		options.setPrettyFlow(true);
		Yaml yaml = new Yaml(options);
		
		try {
			PrintWriter writer = new PrintWriter("config.yaml");
			yaml.dump(config, writer);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			Dialogs.error("Something wen't wrong when saving configuration", window);
		}
	}
	
	Font getFont() {
		try {
			return new Font((String) config.get("fontFamily"), (int) config.get("fontStyle"), (int) config.get("fontSize"));
		} catch (Exception e) {
			Dialogs.error("Something wen't wrong when loading the configured font, using defaults", window);
		}
		setFont(new Font((String) defaults.get("fontFamily"), (int) defaults.get("fontStyle"), (int) defaults.get("fontSize")));
		return getFont();
	}
	
	void setFont(Font font) {
		config.put("fontFamily", font.getFamily());
		config.put("fontStyle", font.getStyle());
		config.put("fontSize", font.getSize());
	}

}
