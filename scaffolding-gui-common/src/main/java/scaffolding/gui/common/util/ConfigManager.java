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
        try {
            ConfigManager configManager = new ConfigManager("application.properties");

            String projectName = configManager.getProperty("project.name");
            String dbName = configManager.getProperty("database.name");
            String dbUsername = configManager.getProperty("database.username");
            String dbPassword = configManager.getProperty("database.password");

            System.out.println("Project Name: " + projectName);
            System.out.println("Database Name: " + dbName);
            System.out.println("Database Username: " + dbUsername);
            System.out.println("Database Password: " + dbPassword);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
