package org.yj.smtpstub.configuration;

import org.slf4j.Logger;
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
    /**
     * logs events in a dedicated stream
     */
    private static final Logger logger = LoggerFactory.getLogger(Configuration.class);
    /**
     * default configuration file name
     */
    private static final String CONFIG_FILE = "/configuration.properties";
    /**
     * config properties in memory representation
     */
    private static final Properties config = new Properties();
    /**
     * tells if config has already been initialized.
     */
    private static boolean isInit = init();

    /**
     * Initializes
     */
    private static boolean init() {
        if (isInit) {
            return true;
        }
        InputStream inputStream = config.getClass().getResourceAsStream(CONFIG_FILE);

        synchronized (config) {
            if (inputStream == null) {
                config.clear();
                return false;
            }
            try {
                // Load defaults settings
                config.load(inputStream);
                inputStream.close();
                return true;
                // and override them from user settings
            } catch (IOException e) {
                LoggerFactory.getLogger(Configuration.class).error("", e);
            }
        }
        return false;
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
        if (!config.isEmpty() && config.containsKey(key)) {
            return config.getProperty(key);
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
        int value = defaultValue;
        if (config.containsKey(key)) {
            try {
                value = Integer.parseInt(config.getProperty(key));
            } catch (NumberFormatException e) {
                logger.warn("Value for key '" + key + "' was expected to be integer.");
            }
        }
        return value;
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
