package org.example.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new IllegalArgumentException("application.properties not found!");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Error loading properties!", e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}

