package com.testing.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class Config {
    private static final Properties PROPS = new Properties();

    static {
        try (InputStream in = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (in == null) {
                throw new IllegalStateException("config.properties not found in classpath");
            }
            PROPS.load(in);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    private Config() {
    }

    public static String get(String key) {
        String value = PROPS.getProperty(key);
        if (value == null) {
            throw new IllegalStateException("Missing config key: " + key);
        }
        return value.trim();
    }

    public static String getOrDefault(String key, String def) {
        String value = PROPS.getProperty(key);
        return value == null ? def : value.trim();
    }

    public static boolean getBoolean(String key, boolean def) {
        String value = PROPS.getProperty(key);
        return value == null ? def : Boolean.parseBoolean(value.trim());
    }
}
