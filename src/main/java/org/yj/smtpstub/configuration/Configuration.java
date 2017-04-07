package org.yj.smtpstub.configuration;

import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * SMTPStub
 * --------------------------------------------------------
 * Contains and returns some project-specific configuration variables.
 *
 * @author TriYop
 * @since 1.0
 */
public class Configuration {

    private static final String CONFIG_FILE = "/configuration.properties";
    private static final Properties config = new Properties();
    private static boolean isInit = false;

    /**
     * Opens the "{@code configuration.properties}" file and maps data.
     */
    Configuration() {
        init();
    }

    private static void init() {
        InputStream in = config.getClass().getResourceAsStream(CONFIG_FILE);
        if (in==null) {
            config.clear();
            isInit = true;
            return;
        }
        try {
            // Load defaults settings
            config.load(in);
            in.close();
            isInit = true;
            // and override them from user settings
        } catch (IOException e) {
            LoggerFactory.getLogger(Configuration.class).error("", e);
        }
    }

    /**
     * Returns the value of a specific entry from the "{@code configuration.properties}" file.
     *
     * @param key a string representing the value from a key/value couple.
     * @return the value of the key, or an empty string if the key was not found.
     */
    public static String get(String key, final String defaultValue) {
        if (!Configuration.isInit) {
            init();
        }
        if (!config.isEmpty()) {
            if (config.containsKey(key)) {
                return config.getProperty(key);
            }
        }
        return defaultValue;
    }

    /**
     * @param key an integer representing the value from a key/value couple
     * @return
     */
    public static int getInt(String key, int defaultValue) {
        if (!Configuration.isInit) {
            init();
        }
        if (config.containsKey(key)) {
            return Integer.parseInt(config.getProperty(key));
        }
        return defaultValue;
    }

    /**
     * Sets the value of a specific entry.
     *
     * @param key   a string representing the key from a key/value couple.
     * @param value the value of the key.
     */
    public static void set(String key, String value) {
        config.setProperty(key, value);
    }

}
