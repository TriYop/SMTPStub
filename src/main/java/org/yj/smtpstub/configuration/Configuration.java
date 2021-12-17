package org.yj.smtpstub.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class Configuration {
    private static final Logger logger = LoggerFactory.getLogger(Configuration.class);
    protected final Properties config = new Properties();
    protected static boolean isInit = false;

    public static class NotInitializedException extends RuntimeException {

    }

    private Configuration() {
    }

    private static Configuration instance = null;

    /**
     * @param loader
     * @return
     */
    public static Configuration getInstance(ConfigurationLoader loader) {
        if (Configuration.instance == null) {
            logger.debug("Initializing configuration from " + loader.getClass().getCanonicalName());
            synchronized (Configuration.class) {
                Configuration.instance = new Configuration();
                loader.load(Configuration.instance);
            }
        }
        return instance;
    }

    /**
     * @return
     */
    public static Configuration getInstance() {
        if (Configuration.instance == null) {
            throw new NotInitializedException();
        }
        return Configuration.instance;
    }

    /**
     * Returns the value of a specific entry from the "{@code configuration.properties}" file.
     *
     * @param key a string representing the value from a key/value couple.
     * @return the value of the key, or an empty string if the key was not found.
     */
    public String getStringValue(String key, final String defaultValue) {
        if (!config.isEmpty() && config.containsKey(key)) {
            return config.getProperty(key);
        }
        return defaultValue;
    }

    /**
     * @param key an integer representing the value from a key/value couple
     * @return
     */
    public int getIntValue(String key, int defaultValue) {
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
    public void set(String key, String value) {
        config.setProperty(key, value);
        logger.debug("Setting property '" + key + "' to value '" + value);
    }

}
