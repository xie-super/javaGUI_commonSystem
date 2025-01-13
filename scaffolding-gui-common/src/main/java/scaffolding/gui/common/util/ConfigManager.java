package scaffolding.gui.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

    private Properties properties;

    public ConfigManager(String configFilePath) throws IOException {
        loadConfiguration(configFilePath);
    }

    private void loadConfiguration(String configFilePath) throws IOException {
        InputStream input = getClass().getClassLoader().getResourceAsStream(configFilePath);
        if (input == null) {
            throw new IllegalArgumentException("Unable to find configuration file: " + configFilePath);
        }
        properties = new Properties();
        properties.load(input);
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static void main(String[] args) {
    }
}
