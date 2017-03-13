package org.yj.smtpstub.configuration;

import org.apache.commons.io.IOUtils;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

/**
 * Contains and returns some project-specific configuration variables.
 *
 * @author TriYop
 * @since 1.0
 */
public class Configuration {
    //FIXME remove this INSTANCE and make required methods static
    // Configuration INSTANCE = new Configuration();

    private static final String CONFIG_FILE = "/configuration.properties";
    private static final String USER_CONFIG_FILE = ".fakeSMTP.properties";
    private static final Properties config = new Properties();

    /**
     * Opens the "{@code configuration.properties}" file and maps data.
     */
    Configuration() {
        InputStream in = getClass().getResourceAsStream(CONFIG_FILE);
        try {
            // Load defaults settings
            config.load(in);
            in.close();
            // and override them from user settings
            loadFromUserProfile();
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
    public static String get(String key) {
        if (config.containsKey(key)) {
            return config.getProperty(key);
        }
        return "";
    }

    /**
     * @param key an integer representing the value from a key/value couple
     * @return
     */
    public static int getInt(String key) {
        if (config.containsKey(key)) {
            return Integer.parseInt(config.getProperty(key));
        }
        return 0;
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

    /**
     * Saves configuration to file.
     *
     * @param file file to save configuration.
     * @throws IOException
     */
    public static synchronized void saveToFile(File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        try {
            config.store(fos, "Last user settings");
        } finally {
            IOUtils.closeQuietly(fos);
        }
    }

    /**
     * Saves configuration to the {@code .smtpstub.properties} file in user profile directory.
     * Calls {@link Configuration#saveToFile(java.io.File)}.
     *
     * @throws IOException
     */
    public synchronized void saveToUserProfile() throws IOException {
        saveToFile(new File(System.getProperty("user.home"), USER_CONFIG_FILE));
    }

    /**
     * Loads configuration from file.
     *
     * @param file file to load configuration.
     * @throws IOException
     */
    public static void loadFromFile(File file) throws IOException {
        if (file.exists() && file.canRead()) {
            FileInputStream fis = new FileInputStream(file);
            try {
                config.load(fis);
            } finally {
                IOUtils.closeQuietly(fis);
            }
        }
    }

    /**
     * Loads configuration from the .smtpstub.properties file in user profile directory.
     * Calls {@link Configuration#loadFromFile(java.io.File)}.
     *
     * @throws IOException
     */
    public static void loadFromUserProfile() throws IOException {
        loadFromFile(new File(System.getProperty("user.home"), USER_CONFIG_FILE));
    }
}
